package es.webandroid.xing.presentation.list

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

class SearchViewActionExtension {

    companion object {
        fun submitText(text: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return AllOf.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isAssignableFrom(SearchView::class.java)
                    )
                }

                override fun getDescription(): String {
                    return "Set text and submit"
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as SearchView).setQuery(text, true)
                }
            }
        }
        fun typeText(text: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return AllOf.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.isAssignableFrom(SearchView::class.java)
                    )
                }

                override fun getDescription(): String {
                    return "Set text"
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as SearchView).setQuery(text, false)
                }
            }
        }
    }
}
