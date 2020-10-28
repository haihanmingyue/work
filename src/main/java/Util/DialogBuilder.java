package Util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**提示框工具
 * @author StarsOne
 * @date Create in  2019/6/2 0002 20:51
 * @description
 */
public class DialogBuilder {
    private String title, message;
    private JFXButton negativeBtn = null;
    private JFXButton positiveBtn = null;
    private JFXButton naturalBtn = null;
    private Window window;
    private JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
    private Paint negativeBtnPaint = Paint.valueOf("#747474");//否定按钮文字颜色，默认灰色
    private Paint positiveBtnPaint = Paint.valueOf("#747474");
    private Paint naturalBtnPaint = Paint.valueOf("#747474");
    private Hyperlink hyperlink = null;
    private JFXAlert<String> alert;



    /**
     * 构造方法
     *
     * @param control 任意一个控件
     */
    public DialogBuilder(Control control) {
        window = control.getScene().getWindow();

    }

    public DialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public DialogBuilder setNegativeBtn(String negativeBtnText) {
        return setNegativeBtn(negativeBtnText, null, null);
    }


    /**
     * 设置否定按钮文字和文字颜色
     *
     * @param negativeBtnText 文字
     * @param color           文字颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setNegativeBtn(String negativeBtnText, String color) {
        return setNegativeBtn(negativeBtnText, null, color);
    }

    /**
     * 设置按钮文字和按钮文字颜色，按钮监听器和
     *
     * @param negativeBtnText
     * @param negativeBtnOnclickListener
     * @param color                      文字颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setNegativeBtn(String negativeBtnText,  OnClickListener negativeBtnOnclickListener, String color, String xxx) {
        if (color != null) {
            this.negativeBtnPaint = Paint.valueOf(color);
        }
        return setNegativeBtn(negativeBtnText, negativeBtnOnclickListener, xxx);
    }


    /**
     * 设置按钮文字和点击监听器
     *
     * @param negativeBtnText            按钮文字
     * @param negativeBtnOnclickListener 点击监听器
     * @return
     */
    public DialogBuilder setNegativeBtn(String negativeBtnText,  OnClickListener negativeBtnOnclickListener,String style) {

        negativeBtn = new JFXButton(negativeBtnText);
        negativeBtn.setCancelButton(true);
        negativeBtn.setStyle(style);
        negativeBtn.setTextFill(negativeBtnPaint);
        negativeBtn.setButtonType(JFXButton.ButtonType.FLAT);
        negativeBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                negativeBtn.setCursor(Cursor.HAND);
            }
        });
        negativeBtn.setOnAction(addEvent -> {
            alert.hideWithAnimation();
            if (negativeBtnOnclickListener != null) {
                negativeBtnOnclickListener.onClick();
            }
        });
        return this;
    }


    /**
     * 设置按钮文字和颜色
     *
     * @param naturalBtnText 文字
     * @param color           颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setNaturalBtn(String naturalBtnText, String color) {
        return setNaturalBtn(naturalBtnText, null, color);
    }

    /**
     * 设置按钮文字，颜色和点击监听器
     *
     * @param naturalBtnText           文字
     * @param naturalBtnOnclickListener 点击监听器
     * @param color                      颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setNaturalBtn(String naturalBtnText,  OnClickListener naturalBtnOnclickListener, String color,String style) {
        this.naturalBtnPaint = Paint.valueOf(color);
        return setNaturalBtn(naturalBtnText, naturalBtnOnclickListener,style);
    }

    /**
     * 设置按钮文字和监听器
     *
     * @param naturalBtnText            文字
     * @param naturalBtnOnclickListener
     * @return
     */
    public DialogBuilder setNaturalBtn(String naturalBtnText, OnClickListener naturalBtnOnclickListener,String style) {
        naturalBtn = new JFXButton(naturalBtnText);
        naturalBtn.setTextFill(naturalBtnPaint);
        naturalBtn.setStyle(style);
        naturalBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                naturalBtn.setCursor(Cursor.HAND);
            }
        });
        naturalBtn.setOnAction(e -> {
            alert.hideWithAnimation();
            System.out.println("执行setNaturalBtn");
            if (naturalBtnOnclickListener != null) {
                naturalBtnOnclickListener.onClick();//回调onClick方法
            }
        });
        return this;
    }

    /**
     * 设置按钮文字和颜色
     *
     * @param positiveBtnText 文字
     * @param color           颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setPositiveBtn(String positiveBtnText, String color) {
        return setPositiveBtn(positiveBtnText, null, color);
    }

    /**
     * 设置按钮文字，颜色和点击监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener 点击监听器
     * @param color                      颜色 十六进制 #fafafa
     * @return
     */
    public DialogBuilder setPositiveBtn(String positiveBtnText,  OnClickListener positiveBtnOnclickListener, String color,String style) {
        this.positiveBtnPaint = Paint.valueOf(color);
        return setPositiveBtn(positiveBtnText, positiveBtnOnclickListener,style);
    }

    /**
     * 设置按钮文字和监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener
     * @return
     */
    public DialogBuilder setPositiveBtn(String positiveBtnText, OnClickListener positiveBtnOnclickListener,String style) {
        positiveBtn = new JFXButton(positiveBtnText);
        positiveBtn.setDefaultButton(true);
        positiveBtn.setTextFill(positiveBtnPaint);
        positiveBtn.setStyle(style);
        positiveBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                positiveBtn.setCursor(Cursor.HAND);
            }
        });
        positiveBtn.setOnAction(e -> {
            alert.hideWithAnimation();
            System.out.println("执行setPostiveBtn");
            if (positiveBtnOnclickListener != null) {
                positiveBtnOnclickListener.onClick();//回调onClick方法
            }
        });
        return this;
    }
    public DialogBuilder setHyperLink(String text) {
        hyperlink = new Hyperlink(text);
        hyperlink.setBorder(Border.EMPTY);
        hyperlink.setOnMouseClicked(event -> {
            if (text.contains("www") || text.contains("com") || text.contains(".")) {
                try {
                    Desktop.getDesktop().browse(new URI(text));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (text.contains(File.separator)) {
                try {
                    Desktop.getDesktop().open(new File(text));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return this;
    }

    /**
     * 创建对话框并显示
     *
     * @return JFXAlert<String>
     */
    public JFXAlert<String> create(int a,int b) {
        alert = new JFXAlert<>((Stage) (window));
        alert.setSize(a,b);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        //添加hyperlink超链接文本
        if (hyperlink != null) {
            layout.setBody(new HBox(new Label(this.message), hyperlink));
        } else {
            layout.setBody(new VBox(new Label(this.message)));
        }
        //添加确定和取消按钮
        if ( naturalBtn != null && negativeBtn != null && positiveBtn != null ) {
            layout.setActions(naturalBtn, negativeBtn, positiveBtn);
        } else {
            if (naturalBtn!= null && negativeBtn != null) {
                layout.setActions(naturalBtn,negativeBtn);
            } else if (naturalBtn != null && positiveBtn != null) {
                layout.setActions(naturalBtn,positiveBtn);
            }else if (positiveBtn != null && negativeBtn != null) {
                layout.setActions(negativeBtn,positiveBtn);
            } else if (naturalBtn != null ) {
                layout.setActions(naturalBtn);
            }else if (positiveBtn != null ) {
                layout.setActions(positiveBtn);
            }else if (negativeBtn != null ) {
                layout.setActions(negativeBtn);
            }
        }

        alert.setContent(layout);
        alert.showAndWait();

        return alert;
    }

    public interface OnClickListener {
        void onClick();
    }

}

