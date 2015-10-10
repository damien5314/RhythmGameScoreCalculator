package mock

import com.ddiehl.rgsc.itg.IITGView
import com.ddiehl.rgsc.itg.ITGScore
import rx.Observable
import rx.android.widget.OnTextChangeEvent

class MockITGView : IITGView {
    override var fantastics: Int = 0
    override var excellents: Int = 0
    override var greats: Int = 0
    override var decents: Int = 0
    override var wayoffs: Int = 0
    override var misses: Int = 0
    override var holds: Int = 0
    override var totalHolds: Int = 0
    override var mines: Int = 0
    override var rolls: Int = 0
    override var totalRolls: Int = 0

    override fun displayInput(score: ITGScore) {
        fantastics = score.fantastics
        excellents = score.excellents
        greats = score.greats
        decents = score.decents
        wayoffs = score.wayoffs
        misses = score.misses
        holds = score.holds
        totalHolds = score.totalHolds
        mines = score.mines
        rolls = score.rolls
        totalRolls = score.totalRolls
    }

    override fun showNoStepsError() {
        /* no-op */
    }

    override fun showHoldsInvalid() {
        /* no-op */
    }

    override fun showRollsInvalid() {
        /* no-op */
    }

    override fun clearErrors() {
        /* no-op */
    }

    override fun clearForm() {
        fantastics = 0
        excellents = 0
        greats = 0
        decents = 0
        wayoffs = 0
        misses = 0
        holds = 0
        totalHolds = 0
        mines = 0
        rolls = 0
        totalRolls = 0
    }

    override fun showEarned(earned: Int) {
        /* no-op */
    }

    override fun showPotential(potential: Int) {
        /* no-op */
    }

    override fun showScorePercentage(scorePercent: Double) {
        /* no-op */
    }

    override fun showScoreGrade(gradeString: String) {
        /* no-op */
    }

    override fun getTextChangedObservable(): Observable<OnTextChangeEvent> {
        return Observable.just(null)
    }
}