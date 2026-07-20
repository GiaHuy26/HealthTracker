package com.example.healthtracker.domain.model

import com.example.healthtracker.R

enum class ActivityType(val nameResId: Int, val met: Double) {
    WALKING(R.string.activity_walking, 3.5),
    RUNNING(R.string.activity_running, 9.8),
    CYCLING(R.string.activity_cycling, 7.5),
    SWIMMING(R.string.activity_swimming, 8.0),
    YOGA(R.string.activity_yoga, 2.5),
    GYM(R.string.activity_gym, 5.0),
    STAIR_CLIMBING(R.string.activity_stair_climbing, 9.0),
    JUMP_ROPE(R.string.activity_jump_rope, 11.0),
    BADMINTON(R.string.activity_badminton, 5.5),
    SOCCER(R.string.activity_soccer, 7.0),
    BASKETBALL(R.string.activity_basketball, 8.0),
    TABLE_TENNIS(R.string.activity_table_tennis, 4.0),
    TENNIS(R.string.activity_tennis, 7.3),
    DANCING(R.string.activity_dancing, 4.5),
    AEROBICS(R.string.activity_aerobics, 6.5)
}
