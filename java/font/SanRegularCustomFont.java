package font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SanRegularCustomFont
  extends TextView
{
  public static Typeface FONT_NAME;
  
  public SanRegularCustomFont(Context paramContext)
  {
    super(paramContext);
    isInEditMode();
  }
  
  public SanRegularCustomFont(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    isInEditMode();
  }
  
  public SanRegularCustomFont(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    isInEditMode();
  }
  
  public void setTypeface(Typeface paramTypeface, int style) {
    if (!isInEditMode()) {

      Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.otf");
      Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/source-sans-pro.bold.ttf");
      if (style == 1) {
        super.setTypeface(boldTypeface);
      } else {
        super.setTypeface(normalTypeface);
      }
    }
  }


}


/* Location:              F:\Android\decompiler\dex2jar-2.0\classes-dex2jar.jar!\com\brainmagic\leatherexport\font\SanRegularCustomFont.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */