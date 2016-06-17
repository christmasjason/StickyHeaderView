package com.christmas.stickyheaderview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    assert fab != null;
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    initRecyclerView();
  }

  private void initRecyclerView() {
    RecyclerView rvStickyExample = (RecyclerView) findViewById(R.id.rv_sticky_example);
    final TextView tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);

    assert rvStickyExample != null;
    assert tvStickyHeaderView != null;

    rvStickyExample.setLayoutManager(new LinearLayoutManager(this));
    rvStickyExample.setAdapter(new StickyExampleAdapter(this, DataUtil.getData()));
    rvStickyExample.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

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
    });
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
