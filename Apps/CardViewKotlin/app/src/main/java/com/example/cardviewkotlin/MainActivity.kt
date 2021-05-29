package com.example.cardviewkotlin

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.util.*


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: AlbumsAdapter? = null
    private var albumList: MutableList<Album>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        initCollapsingToolbar()
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        albumList = ArrayList()
        adapter = AlbumsAdapter(this, albumList as ArrayList<Album>)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
        prepareAlbums()
        try {
            Glide.with(this).load(R.drawable.cover)
                .into(findViewById<View>(R.id.backdrop) as ImageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private fun initCollapsingToolbar() {
        val collapsingToolbar =
            findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbar) as AppBarLayout
        appBarLayout.setExpanded(true)

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    /**
     * Adding few albums for testing
     */
    private fun prepareAlbums() {
        val covers = intArrayOf(
            R.drawable.album1,
            R.drawable.album2,
            R.drawable.album3,
            R.drawable.album4,
            R.drawable.album5,
            R.drawable.album6,
            R.drawable.album7,
            R.drawable.album8,
            R.drawable.album9,
            R.drawable.album10,
            R.drawable.album11,
            R.drawable.album12,
            R.drawable.album13,
            R.drawable.album14,
            R.drawable.album15,
            R.drawable.album16,
            R.drawable.album17,
            R.drawable.album18,
            R.drawable.album19,
            R.drawable.album20
        )
        var a = Album("Piece of Mind", 9, covers[0])
        albumList!!.add(a)
        a = Album("Metallica", 12, covers[1])
        albumList!!.add(a)
        a = Album("Master of Reality", 8, covers[2])
        albumList!!.add(a)
        a = Album("Definitely Maybe", 12, covers[3])
        albumList!!.add(a)
        a = Album("BadMotorFinger", 12, covers[4])
        albumList!!.add(a)
        a = Album("Elvis Presley", 12, covers[5])
        albumList!!.add(a)
        a = Album("Dirt", 13, covers[6])
        albumList!!.add(a)
        a = Album("Muddy Waters at Newport", 9, covers[7])
        albumList!!.add(a)
        a = Album("Let it Be", 12, covers[8])
        albumList!!.add(a)
        a = Album("Misfits Collection ", 20, covers[9])
        albumList!!.add(a)
        a = Album("Nevermind ", 13, covers[10])
        albumList!!.add(a)
        a = Album("London Calling ", 19, covers[11])
        albumList!!.add(a)
        a = Album("Led Zeppellin II ", 13, covers[12])
        albumList!!.add(a)
        a = Album("Some Girls ", 10, covers[13])
        albumList!!.add(a)
        a = Album("Aladdin Sane ", 15, covers[14])
        albumList!!.add(a)
        a = Album("Ramones ", 14, covers[15])
        albumList!!.add(a)
        a = Album("Burn ", 12, covers[16])
        albumList!!.add(a)
        a = Album("Morrison Hotel ", 11, covers[17])
        albumList!!.add(a)
        a = Album("Van Halen ", 11, covers[18])
        albumList!!.add(a)
        a = Album("Pendulum ", 10, covers[19])
        albumList!!.add(a)
        adapter!!.notifyDataSetChanged()
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) :
        ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }
}
