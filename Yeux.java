	import java.util.ArrayList;
	import java.util.Arrays;

import lejos.hardware.BrickFinder;
	import lejos.hardware.Button;
	import lejos.hardware.ev3.LocalEV3;
	import lejos.hardware.lcd.GraphicsLCD;
	import lejos.hardware.motor.EV3LargeRegulatedMotor;
	import lejos.hardware.port.Port;
	import lejos.hardware.sensor.EV3UltrasonicSensor;
	import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
	import lejos.utility.Delay;
	import lejos.hardware.port.MotorPort;
	import lejos.utility.Delay;
	import lejos.hardware.sensor.EV3IRSensor;
	
	
	
public class Yeux {
	
	Port support;
	SensorModes sensor;
	
	//constructeur
 public Yeux (String ch) {
	//ch correspond au port ou est branché le capteur des yeux
	 support=LocalEV3.get().getPort(ch);
	 sensor=new EV3UltrasonicSensor(support);
 }
	
	public int detectInfinity() {
		if(getDistance()>1 && getDistance()<255) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public float getDistance() {	
	SampleProvider distance= sensor.getMode("Distance");
	float[] sample = new float[distance.sampleSize()];
	try {
		distance.fetchSample(sample, 0);
		//Button.ENTER.waitForPress();		
		} catch (Throwable t) { //need lever une illegalargumentExcetion, sensor mode
			t.printStackTrace();
			System.exit(0);
		}
	return (sample[0]*100);//converti en cm et marche
	}
	
	//palais à 32,5 cm
	//comparer les distances
	
	public String avancerPalaisMur() {//avance vers un palais ou mur et quand est sur de ce qui est devant lui, s'arrete et renvoi l'objet
		String type="";
		try {
		
		avancer a=new avancer();
		if(detectInfinity()==1) {//ca veut dire qu'il detecte quelque chose
			float PremDist=getDistance();
			a.avancer1(); //avance et s'arrete pas 
			while(true) {
						float DeuxDistance=getDistance();
						if(DeuxDistance<32) {
						type="mur";
							break;
						}
						
						if(DeuxDistance>PremDist && PremDist<33) {
							type="palais";
							break;
						}
						if(DeuxDistance>PremDist && PremDist>33) {
							type="décalé";
							break;
						}
						
						PremDist=DeuxDistance;
						Delay.msDelay(100);;//attend avant de relancer le while pour eviter de boucler dans rien
						
						//s'avancer vers s'arrête quand la distance augmente: arrete si c'est unmur, si c'est un palais si je suis décalé si précédente valeur était à 32 c'est palais mais si c'atit 50 c'ets que décalé
						// tant que méthode teste les 3 conditions. percevoir environnement toute les 50milliseconde, sinon bouvle trop vite. tant que vrai, si return
				}
			a.arretT();//arret des moteurs car sinon continu à avancer
			 //je suis sois à 32cm du palais si type=p, need tourner si décalé et si mur tourner aussi
			}
		else {
			type="je detecte r";
		}
		
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
		System.exit(0);
		}
		return type; 
}
	//tourner pour detect palais
	
	public void tournerAutour() {
		try {
			double tab[]=new double[1000];
			//instruction de touner
			if ((detectInfinity()==-1)) {
				
			}
			else {
				while(/*fonction de tourner*/) {
					//je met dans mon tableau les différence de distance
					int i=0;
					tab[i]=getDistance();
					//mettre un temps de 5sec pour pas avoir trop de données
					i++;
				}
				//mtn je trie mon tableau en dégageant les valeurs <32,5 car c'est pas des palets et je vais vers la plus petite après
				ArrayList list = new ArrayList(Arrays.asList(tab));
				for(int i=0; i<tab.length; i++) {
					if(tab[i]<=32.5) {
						tab[i]=tab[i+1];
						
					}
				}
			
			}
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
		System.exit(0);
		}
	}
	
		
		public static void main(String args[]) {
		
		Yeux a= new Yeux("S3");	
		/*float distance;
			distance=a.getDistance();
			System.out.println(distance);
			Delay.msDelay(3000);*/
		//a.detectPalais();
		//System.out.print(a.detectInfinity());
		//Delay.msDelay(3000);
		
		
		//System.out.print(a.getDistance());
		//Delay.msDelay(3000);
		System.out.print(a.avancerPalaisMur());
		/*System.out.println();
		Delay.msDelay(3000);*/
		
	}
}

