package usa.devrocoding.synergy.assets.letters;

import usa.devrocoding.synergy.assets.letters.object.Letter;
import usa.devrocoding.synergy.assets.letters.object.Number;

import java.util.ArrayList;
import java.util.List;

public class LetterGenerator {

    public List<StringBuilder> generateFromText(String text){
        List<StringBuilder> builder = new ArrayList<>();
        String[] letterArray = text.split("");
        for(int i=0;i<letterArray.length;i++){
            if (letterArray[i].matches(".*\\d+.*")) {
                Number number = Number.getNumber(Integer.valueOf(letterArray[i]));
                for(int c=0;c<=number.getConverted().length-1;c++){
                    StringBuilder builder1;

                    if (!builder.isEmpty() &&
                            i != 0){
                        builder1 = builder.get(c);
                    }else{
                        builder1 = new StringBuilder();
                    }
                    builder1.append(number.getConverted()[c]);

                    if (i==0){
                        builder.add(builder1);
                    }
                }
            }else{
                Letter letter = Letter.valueOf(letterArray[i].toUpperCase());
                builder = new ArrayList<>();
                for(int c=0;c<letter.getConverted().length;c++){
                    StringBuilder builder1;

                    if (!builder.isEmpty() &&
                            i != 0){
                        builder1 = builder.get(c);
                    }else{
                        builder1 = new StringBuilder();
                    }
                    builder1.append(letter.getConverted()[c]);

                    if (i==0){
                        builder.add(builder1);
                    }
                }
            }
        }
        return builder;
    }

}
