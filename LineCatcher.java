package com.yu;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.parser.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class LineCatcher{
    public static List<LinePath> processToken(byte[] pageBytes) throws IOException{
        List<Float> buffer = new ArrayList<>();
        Stack<Matrix> matrixStack = new Stack<>();
        List<LinePath> lineList = new ArrayList<>();

        PRTokeniser tokeniser = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(pageBytes)));

        PRTokeniser.TokenType tokenType;
        String tokenValue;

        //moveto move the current point

        boolean inGraphicState = false;
        int bufferSize = 0;

        while(tokeniser.nextToken()) {
            tokenType = tokeniser.getTokenType();
            tokenValue = tokeniser.getStringValue();


            if (tokenType == PRTokeniser.TokenType.NUMBER && inGraphicState) {
                buffer.add(Float.parseFloat(tokenValue));
            } else if (tokenType == PRTokeniser.TokenType.OTHER) {
                switch(tokenValue){
                    case "q":
                        inGraphicState = true;
                        matrixStack.push(new Matrix());
                        break;
                    case "cm":
                        bufferSize = buffer.size();
                        Matrix matrix = new Matrix(buffer.get(bufferSize - 6),buffer.get(bufferSize - 5),
                                buffer.get(bufferSize - 4), buffer.get(bufferSize - 3),
                                buffer.get(bufferSize - 2),buffer.get(bufferSize - 1));
                        matrixStack.push(matrix.multiply(matrixStack.pop()));
                        break;
                    case "Q":
                        inGraphicState = false;
                        matrixStack.pop();
                        break;
                    case "m":
                        bufferSize = buffer.size();
                        if(!matrixStack.empty()){
                            float sx = matrixStack.peek().get(Matrix.I31);
                            float sy = matrixStack.peek().get(Matrix.I32);
                            sx += buffer.get(bufferSize - 2);
                            sy += buffer.get(bufferSize - 1);
                            lineList.add(new LinePath(sx,sy,0,0));
                        }
                        break;
                    case "l":
                        bufferSize = buffer.size();
                        float w = buffer.get(bufferSize - 2);
                        float h = buffer.get(bufferSize - 1);
                        if(!lineList.isEmpty()){
                            LinePath lastLine = lineList.get(lineList.size() - 1);
                            lastLine.setW(w);
                            lastLine.setH(h);
                        }
                        break;
                }
            }
        }
        return lineList;
    }
}