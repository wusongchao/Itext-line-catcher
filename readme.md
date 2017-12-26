Based on Itext.


#HOW TO USE IT

Firstly, retrieve the token stream.



```java
PdfReader pdfReader = new PdfReader(filePath);
byte[] pageBytes = pdfReader.getPageContent(pageNum);000
```

Then:

```java
List<LinePath> linePaths = LineCatcher.processToken(pageBytes);
```

That's it.

You can use the result to extract the text with underdecoration.
```java
    for(LinePath line : linePaths){
        if(UtilMethods.isSameRange(lby,line.getSy(),verticalAdjustedValue) &&
        lbx >= line.getSx() - horizontalAdjustedValue &&
                rtx <= line.getSx() + line.getW() + horizontalAdjustedValue){
            underlined = true;
            break;
        }
    }
```


Take this page as example:
![Alt text](https://github.com/wusongchao/Itext-line-catcher/raw/master/screenshots/sourcepdf.png)

And here's the result:
![Alt text](https://github.com/wusongchao/Itext-line-catcher/raw/master/screenshots/result.png)