package com.dc.transmission.glObjects;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.dc.transmission.glCore.MatrixState;
import com.dc.transmission.glCore.ShaderUtil;
import com.dc.transmission.glCore.TGLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.dc.transmission.glCore.ShaderUtil.createProgram;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class RectTex {
    private static float UNIT_SIZE=1f;

    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用id
    int maPositionHandle; //顶点位置属性引用id
    int maTexCoorHandle; //顶点纹理坐标属性引用id
    String mVertexShader;//顶点着色器
    String mFragmentShader;//片元着色器
    static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲
    int vCount=0;
    float xAngle=0;//绕x轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度

    float sRange;//s纹理坐标范围
    float tRange;//t纹理坐标范围

    public RectTex(TGLSurfaceView glSv){
        initVertexData();
        initShader(glSv);
    }

    public void initVertexData() {
        vCount=6;
        final float UNIT_SIZE=0.3f;
        float vertices[]=new float[]{
                        -4*UNIT_SIZE,4*UNIT_SIZE,0,
                        -4*UNIT_SIZE,-4*UNIT_SIZE,0,
                        4*UNIT_SIZE,-4*UNIT_SIZE,0,
                        4*UNIT_SIZE,-4*UNIT_SIZE,0,
                        4*UNIT_SIZE,4*UNIT_SIZE,0,
                        -4*UNIT_SIZE,4*UNIT_SIZE,0};

        //创建顶点坐标数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置

        float texCoor[]=new float[]{
                        0,0,0,tRange,
                        sRange,tRange,sRange,tRange,
                        sRange,0,0,0};
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = cbb.asFloatBuffer();
        mTexCoorBuffer.put(texCoor);
        mTexCoorBuffer.position(0);
    }

    public void initShader(TGLSurfaceView glSv) {
        //加载顶点着色器的脚本内容
        mVertexShader= ShaderUtil.loadFromAssetsFile("vertex.sh", glSv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", glSv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用id
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void draw(int texId) {
        //制定使用某套shader程序
        GLES20.glUseProgram(mProgram);
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix,0,0,0,1,0);
        //设置沿Z轴正向位移1
        Matrix.translateM(mMMatrix,0,0,0,1);
        //设置绕y轴旋转
        Matrix.rotateM(mMMatrix,0,yAngle,0,1,0);
        //设置绕z轴旋转
        Matrix.rotateM(mMMatrix,0,zAngle,0,0,1);
        //设置绕x轴旋转
        Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);
        //将最终变换矩阵传入shader程序
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0);
        //为画笔指定顶点位置数据
        GLES20.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES20.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //为画笔指定顶点纹理坐标数据
        GLES20.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES20.GL_FLOAT,
                        false,
                        2*4,
                        mTexCoorBuffer
                );
        //允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);

        //绑定纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);

        //绘制纹理矩形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }
}
