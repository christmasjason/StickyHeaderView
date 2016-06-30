package com.christmas.stickyheaderview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private List<StickyExampleModel> stickyExampleModels = new ArrayList<>();
  private StickyExampleAdapter stickyExampleAdapter;
  private boolean loadingMore = false;
  private boolean noMoreData = false;
  private int currentPage = 1;

  private LoadMoreTask loadMoreTask;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    initRecyclerView();
  }

  private void initRecyclerView() {
    RecyclerView rvStickyExample = (RecyclerView) findViewById(R.id.rv_sticky_example);
    final TextView tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);

    assert rvStickyExample != null;
    assert tvStickyHeaderView != null;

    executeLoadMore();

    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    rvStickyExample.setLayoutManager(linearLayoutManager);
    stickyExampleAdapter = new StickyExampleAdapter(this, stickyExampleModels);
    rvStickyExample.setAdapter(stickyExampleAdapter);
    rvStickyExample.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        dealWithLoadMore(dy, linearLayoutManager);

        dealWithStickyHeader(recyclerView, tvStickyHeaderView);
      }
    });
  }

  private void dealWithLoadMore(int dy, LinearLayoutManager linearLayoutManager) {
    // Scroll up.
    if (dy > 0) {
      int totalItemCount = linearLayoutManager.getItemCount();
      int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
      int visibleItemCount = linearLayoutManager.getChildCount();
      if (!loadingMore && !noMoreData &&
          (firstVisibleItemPosition + visibleItemCount) >= totalItemCount) {
        loadingMore = true;
        executeLoadMore();
      }
    }
  }

  private void executeLoadMore() {
    loadMoreTask = new LoadMoreTask();
    loadMoreTask.execute(DateUtils.SECOND_IN_MILLIS * 5);
  }

  private void dealWithStickyHeader(RecyclerView recyclerView, TextView tvStickyHeaderView) {
    // Get the sticky information from the topmost view of the screen.
    View stickyInfoView = recyclerView.findChildViewUnder(
        tvStickyHeaderView.getMeasuredWidth() / 2, 5);

    if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
      tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
    }

    // Get the sticky view's translationY by the first view below the sticky's height.
    View transInfoView = recyclerView.findChildViewUnder(
        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 1);

    if (transInfoView != null && transInfoView.getTag() != null) {
      int transViewStatus = (int) transInfoView.getTag();
      int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();
      if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
        // If the first view below the sticky's height scroll off the screen,
        // then recovery the sticky view's translationY.
        if (transInfoView.getTop() > 0) {
          tvStickyHeaderView.setTranslationY(dealtY);
        } else {
          tvStickyHeaderView.setTranslationY(0);
        }
      } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
        tvStickyHeaderView.setTranslationY(0);
      }
    }
  }

  private class LoadMoreTask extends AsyncTask<Long, Object, List<StickyExampleModel>> {
    @Override
    protected List<StickyExampleModel> doInBackground(Long... params) {
      try {
        Thread.sleep(params[0]);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      return DataUtil.getData(currentPage);
    }

    @Override
    protected void onPostExecute(List<StickyExampleModel> stickyExampleModelList) {
      super.onPostExecute(stickyExampleModelList);

      currentPage++;
      loadingMore = false;
      if (stickyExampleModelList == null || stickyExampleModelList.size() == 0) {
        noMoreData = true;
        stickyExampleAdapter.setNoMoreData(true);
      } else if (stickyExampleModelList.size() < DataUtil.MODEL_COUNT) {
        noMoreData = true;
        stickyExampleAdapter.setNoMoreData(true);
        stickyExampleModels.addAll(stickyExampleModelList);
      } else {
        stickyExampleModels.addAll(stickyExampleModelList);
      }
      stickyExampleAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
