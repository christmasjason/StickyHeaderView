# StickyHeaderView
A simple sticky header view implement.

Add a scroll listener to the recyclerView, according the recyclerView's scroll distance to stick the sticky header view.

# Screen record
![image](https://github.com/christmasjason/StickyHeaderView/blob/master/app/src/main/res/screen_record/sticky.gif)

# Core code
```
final TextView tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
```

