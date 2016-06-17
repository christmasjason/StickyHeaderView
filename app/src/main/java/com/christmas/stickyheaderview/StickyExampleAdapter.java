package com.christmas.stickyheaderview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class StickyExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public static final int FIRST_STICKY_VIEW = 1;
  public static final int HAS_STICKY_VIEW = 2;
  public static final int NONE_STICKY_VIEW = 3;

  private Context context;
  private List<StickyExampleModel> stickyExampleModels;

  public StickyExampleAdapter(Context context, List<StickyExampleModel> recyclerViewModels) {
    this.context = context;
    this.stickyExampleModels = recyclerViewModels;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
    return new RecyclerViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    if (viewHolder instanceof RecyclerViewHolder) {
      RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
      if (position % 2 == 0) {
        recyclerViewHolder.rlContentWrapper.setBackgroundColor(
            ContextCompat.getColor(context, R.color.bg_color_1));
      } else {
        recyclerViewHolder.rlContentWrapper.setBackgroundColor(
            ContextCompat.getColor(context, R.color.bg_color_2));
      }

      StickyExampleModel stickyExampleModel = stickyExampleModels.get(position);
      recyclerViewHolder.tvName.setText(stickyExampleModel.name);
      recyclerViewHolder.tvGender.setText(stickyExampleModel.gender);
      recyclerViewHolder.tvProfession.setText(stickyExampleModel.profession);

      if (position == 0) {
        recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
        recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
        recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
      } else {
        if (!TextUtils.equals(stickyExampleModel.sticky, stickyExampleModels.get(position - 1).sticky)) {
          recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
          recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
          recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
        } else {
          recyclerViewHolder.tvStickyHeader.setVisibility(View.GONE);
          recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
        }
      }
      recyclerViewHolder.itemView.setContentDescription(stickyExampleModel.sticky);
    }
  }

  @Override
  public int getItemCount() {
    return stickyExampleModels == null ? 0 : stickyExampleModels.size();
  }

  public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView tvStickyHeader;
    public RelativeLayout rlContentWrapper;
    public TextView tvName;
    public TextView tvGender;
    public TextView tvProfession;

    public RecyclerViewHolder(View itemView) {
      super(itemView);

      tvStickyHeader = (TextView) itemView.findViewById(R.id.tv_sticky_header_view);
      rlContentWrapper = (RelativeLayout) itemView.findViewById(R.id.rl_content_wrapper);
      tvName = (TextView) itemView.findViewById(R.id.tv_name);
      tvGender = (TextView) itemView.findViewById(R.id.tv_gender);
      tvProfession = (TextView) itemView.findViewById(R.id.tv_profession);
    }
  }
}
