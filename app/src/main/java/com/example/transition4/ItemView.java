package com.example.transition4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.webka.sdk.players.view.PlayerTextureView;

import static java.util.Optional.ofNullable;


/**
 * @author Konstantin Epifanov
 * @since 22.11.2019
 */
public class ItemView extends FrameLayout {

  private PlayerTextureView mTextureView;

  private Item mItem = null;

  public ItemView(Context context) {
    this(context, null);
  }

  public ItemView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mTextureView = findViewById(R.id.texture);
    mTextureView.setChecked(true);
  }

  @Override
  public void setTag(Object tag) {
    if (tag instanceof Item) {
      mItem = (Item) tag;
      mTextureView.setTag(ofNullable(mItem)
                   .flatMap(i -> ofNullable(i.url))
                   .orElse(null));
    } else {
      mItem = null;
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    System.out.println("ITEM ON SIZE CHANGEDw = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
  }

  /** {@inheritDoc} */
  @Override public final void setScaleX(float value) {
    System.out.println("ITEM setScaleX value = [" + value + "]");
    super.setScaleX(value);
  }

  /** {@inheritDoc} */
  @Override public final void setScaleY(float value) {
    System.out.println("ITEM setScaleY value = [" + value + "]");
    super.setScaleY(value);
  }

  /** {@inheritDoc} */
  @Override
  protected final void finalize() throws Throwable {
    try {mTextureView.close();} finally {super.finalize();}
  }

  /** {@inheritDoc} */
  @SuppressLint("MissingSuperCall")
  @Override public final void draw(Canvas canvas) {
    mTextureView.wrapDraw(() -> superDraw(canvas));
  }

  /** @param canvas canvas for draw */
  private void superDraw(Canvas canvas) {
    super.draw(canvas);
  }

  public static class Item {

    public final int id;
    public final String url;

    public Item(int id, String url) {
      this.id = id;
      this.url = url;
    }

    @Override
    public String toString() {
      return "Item{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true; if (o == null || getClass() != o.getClass()) return false;
      final Item item = (Item) o; return id == item.id;
    }

    @Override
    public int hashCode() {
      return id;
    }
  }
}
