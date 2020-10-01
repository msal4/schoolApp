package com.smart.resources.schools_app.core.appDatabase

import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion

object SqlUtils {
    const val CREATE_ONLINE_EXAMS_TABLE= "CREATE TABLE IF NOT EXISTS `${LocalOnlineExam.TABLE_NAME}` (`onlineExamId` TEXT NOT NULL, `subjectName` TEXT NOT NULL, `date` INTEGER NOT NULL, `examDuration` INTEGER NOT NULL, `startDateTime` INTEGER, `numberOfQuestions` INTEGER NOT NULL, `examStatus` INTEGER NOT NULL, PRIMARY KEY(`onlineExamId`))"
    const val CREATE_QUESTIONS_TABLE= "CREATE TABLE IF NOT EXISTS `${LocalQuestion.TABLE_NAME}` (`questionId` TEXT NOT NULL, `onlineExamId` TEXT NOT NULL, `questionText` TEXT NOT NULL, `questionType` INTEGER NOT NULL, `options` TEXT, PRIMARY KEY(`questionId`), FOREIGN KEY(`onlineExamId`) REFERENCES `OnlineExams`(`onlineExamId`) ON UPDATE CASCADE ON DELETE CASCADE )"
}