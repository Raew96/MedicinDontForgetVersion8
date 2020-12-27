package com.rafalbiarda.medcinedontforgetversion8.models

import java.sql.Timestamp
import java.util.*

class MedicineReminder(
    var timestamp: Long,
    var medicine: Medicine
): Reminder() {
}