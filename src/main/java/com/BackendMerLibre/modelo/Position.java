package com.BackendMerLibre.modelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Position {
	
	 private double x;
	 private  double y;
	 
	 public Position(double[] points){
        this.x = points[0];
        this.y = points[1];
	 }
	 @Override
	 public String toString(){
        return x+","+y;
	 }
}
