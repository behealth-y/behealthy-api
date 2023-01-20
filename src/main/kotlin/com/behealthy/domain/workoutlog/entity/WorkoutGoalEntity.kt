package com.behealthy.domain.workoutlog.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Table(name = "workout_goal")
@Entity
class WorkoutGoalEntity(

    @Id
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var hour: Int = 0,

    @Column(nullable = false)
    var minute: Int = 0

) : Persistable<Long> {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @CreatedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime

    /**
     * [refer](https://kapentaz.github.io/jpa/Spring-Data-JPA%EC%97%90%EC%84%9C-insert-%EC%A0%84%EC%97%90-select-%ED%95%98%EB%8A%94-%EC%9D%B4%EC%9C%A0/)
     */
    override fun getId(): Long = userId

    override fun isNew(): Boolean = true
}