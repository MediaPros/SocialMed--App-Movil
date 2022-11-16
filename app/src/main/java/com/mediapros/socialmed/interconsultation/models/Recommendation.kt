package com.mediapros.socialmed.interconsultation.models

import com.mediapros.socialmed.security.models.User

class Recommendation(
    var id: Int,
    var recommendationUserId: Int,
    var recommendedUserId: Int,
    var userRecommendation: User
)