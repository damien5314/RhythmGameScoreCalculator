package com.ddiehl.rgsc.itg

import rx.Observable
import rx.android.widget.OnTextChangeEvent

interface ITGView {
    var fantastics: Int
    var excellents: Int
    var greats: Int
    var decents: Int
    var wayoffs: Int
    var misses: Int
    var holds: Int
    var totalHolds: Int
    var mines: Int
    var rolls: Int
    var totalRolls: Int
    fun displayInput(score: ITGScore)
    fun showNoStepsError()
    fun showHoldsInvalid()
    fun showRollsInvalid()
    fun clearErrors()
    fun clearForm()
    fun showEarned(earned: Int)
    fun showPotential(potential: Int)
    fun showScorePercentage(scorePercent: Double)
    fun showScoreGrade(gradeString: String)
    fun getTextChangedObservable(): Observable<OnTextChangeEvent>
}
