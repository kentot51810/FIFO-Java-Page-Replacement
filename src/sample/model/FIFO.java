package sample.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FIFO{
    private int frames; //number of frames
    private List<Integer> reference = new ArrayList<>();
    private int mem_layout[][];
    private int hit;
    private int fault = 0;


    public FIFO(int frames, List<Integer> reference) {
        this.frames = frames;
        this.reference = reference;
    }


    public FIFO() {}

    //-------------------------Getter Methods------------------------


    public int getFrames() {
        return frames;
    }

    public Integer getHit() {
        return hit;
    }

    public Integer getFault() {
        return fault;
    }

    public String getHitRatio() {
        DecimalFormat format = new DecimalFormat("0.##");
        double hitRatio = (double)hit /reference.size();
        return format.format(hitRatio);

    }

    public String getFaultRate(){
        DecimalFormat format = new DecimalFormat("0.##");
        double faultRate = ((double) fault / reference.size())*100;
        return "% " + format.format(faultRate);
    }

    //-------------------------Setter Methods------------------------


    public void setFrames(int frames) {
        this.frames = frames;
    }

    public void setReference(List<Integer> reference) {
        this.reference = reference;
    }

    //-----------------------------Calculation Functions---------------------------------
    public String calculate(){
        hit = 0;
        int pointer = 0;

        mem_layout = new int[reference.size()][frames];
        int[] buffer = new int[frames];
        for(int j = 0; j < frames; j++)
            buffer[j] = -1;

        for(int i = 0; i < reference.size(); i++)
        {
            int search = -1;
            for(int j = 0; j < frames; j++)
            {
                if(buffer[j] == reference.get(i))
                {
                    search = j;
                    hit++;
                    break;
                }
            }
            if(search == -1)
            {
                buffer[pointer] = reference.get(i);
                fault++;
                pointer++;
                if(pointer == frames)
                    pointer = 0;
            }
            for(int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < frames; i++)
        {
            for(int j = 0; j < reference.size(); j++)
                output.append(String.format("%3d%5s%3s",mem_layout[j][i], "|", ""));
            output.append("\n");
        }
        return output.toString();

    }
}