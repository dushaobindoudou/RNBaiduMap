package com.rrd.baidumap;

import com.facebook.csslayout.CSSMeasureMode;
import com.facebook.csslayout.CSSNode;
import com.facebook.csslayout.MeasureOutput;
import com.facebook.react.uimanager.LayoutShadowNode;


public class BaiduMapShadowNode extends LayoutShadowNode implements CSSNode.MeasureFunction {
    public BaiduMapShadowNode() {
        setMeasureFunction(this);
    }


    public void measure(CSSNode node, float width, MeasureOutput measureOutput) {
        measureOutput.width = width;
    }


    @Override
    public void measure(CSSNode node, float width, CSSMeasureMode widthMode, float height, CSSMeasureMode heightMode, MeasureOutput measureOutput) {
        measureOutput.width = width;
    }
}
