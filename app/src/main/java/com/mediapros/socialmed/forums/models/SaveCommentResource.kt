package com.mediapros.socialmed.forums.models

class SaveCommentResource(
    var date: String,
    var content: String,
    var userId: Int,
    var forumId: Int
)