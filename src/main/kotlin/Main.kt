package com.lbty

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

fun main() {
  val datasource = HikariConfig().apply {
    val host = "127.0.0.1"
    val port = "5432"
    val path = "postgres"
    val userName = "postgres"
    val password = "123456789"

    driverClassName = "org.postgresql.Driver"
    maximumPoolSize = 3
    jdbcUrl = "jdbc:postgresql://$host:$port/$path?user=$userName&password=$password"
    isAutoCommit = false
    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    validate()
  }.let {
    HikariDataSource(it)
  }
  Database.connect(datasource = datasource)
  transaction {
    SchemaUtils.createMissingTablesAndColumns(TestUpsertTable)
  }

//  transaction {
//    TestUpsertTable.update {
//      it[age] = 13
//      it[tags] = listOf("量子", "虚数")
//
//    }
//  }

  transaction {
    TestUpsertTable.upsert(
      TestUpsertTable.name, TestUpsertTable.number,
      onUpdate = {
        it[TestUpsertTable.age] = 13
        it[TestUpsertTable.tags] = listOf("量子", "虚数")
      },
    ) {
      it[name] = "孤注一掷的掷骰者"
      it[number] = "4086"
      it[age] = 22
      it[tags] = listOf("物理", "冰", "雷", "虚数")
    }
  }

  transaction {
    TestUpsertTable.upsert(
      TestUpsertTable.name, TestUpsertTable.number,
      onUpdate = {
        it[TestUpsertTable.age] = 13
        it[TestUpsertTable.tags] = listOf("量子", "虚数")
      },
      where = {
        TestUpsertTable.name eq "孤注一掷的掷骰者"
      }
    ) {
      it[name] = "孤注一掷的掷骰者"
      it[number] = "4086"
      it[age] = 22
      it[tags] = listOf("物理", "冰", "雷", "风")
    }
  }
}


