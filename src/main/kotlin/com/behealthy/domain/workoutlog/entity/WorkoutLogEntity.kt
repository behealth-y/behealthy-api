package com.behealthy.domain.workoutlog.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Table(name = "workout_log")
@Entity
class WorkoutLogEntity(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var emoji: String,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime,

    @Column(nullable = false)
    var intensity: Intensity,

    var comment: String?
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime

    enum class Intensity(val dbColumn: Int) {
        EASY(1),
        NORMAL(2),
        HARD(3),
        EXTREMELY_HARD(4);

        @javax.persistence.Converter(autoApply = true)
        class Converter : AttributeConverter<Intensity, Int> {
            override fun convertToDatabaseColumn(attribute: Intensity?): Int? {
                return attribute?.dbColumn
            }

            override fun convertToEntityAttribute(dbData: Int?): Intensity {
                return Intensity.values().first { it.dbColumn == dbData }
            }
        }

        companion object
    }
}