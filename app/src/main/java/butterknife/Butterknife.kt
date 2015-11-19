package butterknife

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.support.v4.app.Fragment as SupportFragment
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlin.properties.ReadOnlyProperty
import java.util.WeakHashMap
import java.util.Collections
import kotlin.reflect.KProperty

public fun <T : View> ViewGroup.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)
public fun <T : View> Activity.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)
public fun <T : View> Dialog.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)
public fun <T : View> Fragment.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)
public fun <T : View> SupportFragment.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)
public fun <T : View> ViewHolder.bindView(id: Int):
        ReadOnlyProperty<Any, T> = ViewBinding(id)

public fun <T : View> ViewGroup.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)
public fun <T : View> Activity.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)
public fun <T : View> Dialog.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)
public fun <T : View> Fragment.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)
public fun <T : View> SupportFragment.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)
public fun <T : View> ViewHolder.bindOptionalView(id: Int):
        ReadOnlyProperty<Any, T?> = OptionalViewBinding(id)

public fun <T : View> ViewGroup.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)
public fun <T : View> Activity.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)
public fun <T : View> Dialog.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)
public fun <T : View> Fragment.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)
public fun <T : View> SupportFragment.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)
public fun <T : View> ViewHolder.bindViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = ViewListBinding(ids)

public fun <T : View> ViewGroup.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)
public fun <T : View> Activity.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)
public fun <T : View> Dialog.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)
public fun <T : View> Fragment.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)
public fun <T : View> SupportFragment.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)
public fun <T : View> ViewHolder.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Any, List<T>> = OptionalViewListBinding(ids)

object ButterKnife {
    fun reset(target: Any) {
        LazyRegistry.reset(target)
    }
}

private fun <T : View> findView(thisRef: Any, id: Int): T? {
    @Suppress("UNCHECKED_CAST")
    return when (thisRef) {
        is View -> thisRef.findViewById(id)
        is Activity -> thisRef.findViewById(id)
        is Dialog -> thisRef.findViewById(id)
        is Fragment -> thisRef.getView().findViewById(id)
        is SupportFragment -> thisRef.getView().findViewById(id)
        is ViewHolder -> thisRef.itemView.findViewById(id)
        else -> throw IllegalStateException("Unable to find views on type.")
    } as T?
}

private class ViewBinding<T : View>(val id: Int) : LazyReadOnlyProperty<T>() {
    override fun create(thisRef: Any, desc: KProperty<*>): T =
            findView<T>(thisRef, id)
                    ?: throw IllegalStateException("View ID $id for '${desc.name}' not found.")
}

private class OptionalViewBinding<T : View>(val id: Int) : LazyReadOnlyProperty<T?>() {
    override fun create(thisRef: Any, desc: KProperty<*>): T? =
            findView(thisRef, id)
}

private class ViewListBinding<T : View>(val ids: IntArray) : LazyReadOnlyProperty<List<T>>() {
    override fun create(thisRef: Any, desc: KProperty<*>): List<T> =
            ids.map { id -> findView<T>(thisRef, id)
                    ?: throw IllegalStateException("View ID $id for '${desc.name}' not found.")
            }
}

private class OptionalViewListBinding<T : View>(val ids: IntArray) : LazyReadOnlyProperty<List<T>>() {
    override fun create(thisRef: Any, desc: KProperty<*>): List<T> =
            ids.map { id -> findView<T>(thisRef, id) }.filterNotNull()
}

private abstract class LazyReadOnlyProperty<T : Any?> : ReadOnlyProperty<Any, T> {
    private val lazy: Lazy<T> = Lazy()

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        LazyRegistry.register(thisRef, lazy)
        return create(thisRef, property)
    }

    protected abstract fun create(thisRef: Any, desc: KProperty<*>): T
}

private class Lazy<T> {
    private object EMPTY
    private var value: Any? = EMPTY

    fun get(initializer: () -> T): T {
        if (value == EMPTY) {
            value = initializer.invoke()
        }
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    fun reset() {
        value = EMPTY
    }
}

private object LazyRegistry {
    private val lazyMap = WeakHashMap<Any, MutableCollection<Lazy<*>>>()

    fun register(target: Any, lazy: Lazy<*>) {
        lazyMap.getOrPut(target, { Collections.newSetFromMap(WeakHashMap()) }).add(lazy)
    }

    fun reset(target: Any) {
        lazyMap.get(target)?.forEach { it.reset() }
    }
}