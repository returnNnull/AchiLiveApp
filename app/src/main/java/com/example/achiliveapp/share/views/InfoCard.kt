package com.example.achiliveapp.share.views


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.InfoCardBinding

class InfoCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var tittle: String?
    private var text: String?
    private var icon: Drawable?
    private var divider: Float
    val binding = InfoCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.let {
            it.theme.obtainStyledAttributes(attrs, R.styleable.info_card, defStyleAttr, 0)
                .apply {
                    try {
                        tittle = getString(R.styleable.info_card__tittle)
                        text = getString(R.styleable.info_card__text)
                        icon = getDrawable(R.styleable.info_card__icon)
                        divider = getFloat(R.styleable.info_card__divider, 0f)
                        binding.icon.setImageDrawable(icon)
                        binding.title.text = tittle
                        binding.text.text = text

                    } finally {
                        recycle()
                    }
                }
        }
    }

    fun setTittle(value: String){
        binding.title.text = value
    }

    fun setBody(value: String){
        binding.text.text = value
    }

}