package font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class SanRegularCustomFontEditText
  extends EditText
{
  public static Typeface FONT_NAME;
  
  public SanRegularCustomFontEditText(Context paramContext)
  {
    super(paramContext);
    isInEditMode();
  }
  
  public SanRegularCustomFontEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    isInEditMode();
  }
  
  public SanRegularCustomFontEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    isInEditMode();
  }
  
  public void setTypeface(Typeface paramTypeface, int paramInt)
  {
    if (!isInEditMode())
    {
      paramTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.otf");
      Typeface localTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.otf");
      if (paramInt == 1) {
        super.setTypeface(localTypeface);
      }
    }
    else
    {
      return;
    }
    super.setTypeface(paramTypeface);
  }
}


/* Location:              F:\Android\decompiler\dex2jar-2.0\classes-dex2jar.jar!\com\brainmagic\leatherexport\font\SanRegularCustomFontEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */