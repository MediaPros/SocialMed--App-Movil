package com.mediapros.socialmed.forums.models

class Comment(
    var id: Int,
    var date: String,
    var content: String,
    var userId: Int,
    var forum: Forum
)