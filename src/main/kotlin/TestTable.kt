package com.lbty

import org.jetbrains.exposed.dao.id.IntIdTable

object TestUpsertTable : IntIdTable("test_upsert_table") {
  val name = varchar("name", 50)
  val number = varchar("number", 50)
  val age = integer("age")
  val tags = array<String>("tags")
  val namePhoneUniqueIndex = uniqueIndex(name, number)
}
