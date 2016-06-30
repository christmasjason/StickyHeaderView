package com.christmas.stickyheaderview;

import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
  public static final int MODEL_COUNT = 30;
  public static final int MAX_PAGE = 5;

  public static List<StickyExampleModel> getData(@IntRange(from = 1, to = 5) int currentPage) {
    List<StickyExampleModel> stickyExampleModels = new ArrayList<>();

    int startIndex = MODEL_COUNT * (currentPage - 1);
    int endIndex = MODEL_COUNT * currentPage;

    if (currentPage == MAX_PAGE) {
      endIndex = endIndex - 20;
    }

    int addition = (currentPage - 1) * 4;
    for (int index = startIndex; index < endIndex; index++) {
      if (index < startIndex + 5) {
        stickyExampleModels.add(new StickyExampleModel(
            "吸顶文本" + (currentPage + addition), "name" + index, "gender" + index, "profession" + index));

      } else if (index < startIndex + 15) {
        stickyExampleModels.add(new StickyExampleModel(
            "吸顶文本" + (currentPage + 1 + addition), "name" + index, "gender" + index, "profession" + index));

      } else if (index < startIndex + 25) {
        stickyExampleModels.add(new StickyExampleModel(
            "吸顶文本" + (currentPage + 2 + addition), "name" + index, "gender" + index, "profession" + index));

      } else {
        stickyExampleModels.add(new StickyExampleModel(
            "吸顶文本" + (currentPage + 3 + addition), "name" + index, "gender" + index, "profession" + index));

      }
    }

    return stickyExampleModels;
  }
}
