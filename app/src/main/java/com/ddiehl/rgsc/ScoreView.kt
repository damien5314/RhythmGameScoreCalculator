package com.ddiehl.rgsc

import com.ddiehl.rgsc.data.Score
import rx.Observable

interface ScoreView {
    fun displayInput(score: Score)
    fun showNoStepsError()
    fun clearErrors()
    fun clearForm()
    fun showScoreValues(earned: Int, potential: Int)
    fun showScorePercentage(scorePercent: Double)
    fun showScoreGrade(gradeString: String)
    fun getTextChangedObservable(): Observable<CharSequence>
    fun showScoreError()
}
