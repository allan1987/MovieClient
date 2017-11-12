package com.mnidersoft.movieclient.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Allan.Menezes on 11/12/17.
 */

public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {

    // The total number of items in the dataset after the last load
    private int mPreviousTotal = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean mLoading = true;
    private int mCurrentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    protected EndlessRecyclerScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        //The recycler was refreshed and need to reset to call onLoadMore again
        if (mPreviousTotal > totalItemCount) reset();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 5;
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            mLoading = true;
        }
    }

    private void reset(){
        mPreviousTotal = 0;
        mCurrentPage = 1;
        mLoading = true;
    }

    public abstract void onLoadMore(int currentPage);
}