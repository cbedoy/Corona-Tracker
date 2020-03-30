package iambedoy.coronatracker

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/03/20.
 */
fun TextView.setCollapseExpand(collapsibleView: View, onCollapse: () -> Unit, onExpand: () -> Unit) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_expand), null)
    setOnClickListener {
        when (collapsibleView.visibility) {
            View.GONE -> {
                collapsibleView.visibility = View.VISIBLE
                setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_collapse), null)
                onExpand()
            }
            View.VISIBLE -> {
                collapsibleView.visibility = View.GONE
                setCompoundDrawablesWithIntrinsicBounds(null, null, createDrawable(R.drawable.ic_action_expand), null)
                onCollapse()
            }
        }
    }
}

fun View.createDrawable(resourceId : Int): Drawable? {
    return ContextCompat.getDrawable(context, resourceId)
}