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
	
	
	
public class CapteurUltrason {
	MoteursRoues roue=new MoteursRoues();
	Port support;
	SensorModes sensor;
	
	//constructeur
 public CapteurUltrason (String ch) {
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
	
	
	
	public TypeLastDistance AvancerVers() {//avance vers un palais ou mur et quand est sur de ce qui est devant lui, s'arrete et renvoi l'objet
		TypeLastDistance type = new TypeLastDistance();
		try {
		
		
		if(detectInfinity()==1) {//ca veut dire qu'il detecte quelque chose
			float PremDist=getDistance();
			roue.avancer(); //avance et s'arrete pas 
			while(roue.getEnMouvement()) {
						float DeuxDistance=getDistance();
						if(DeuxDistance<32) {
							type.set('m', PremDist);
							roue.arret();
						}
						else if(DeuxDistance>PremDist && PremDist<33) {
							type.set('p', PremDist);
							roue.arret();
						}
						else if(DeuxDistance>PremDist && PremDist>33) {
							type.set('d', PremDist);
							roue.arret();
						}
						PremDist=DeuxDistance;
						Delay.msDelay(100);;//attend avant de relancer le while pour eviter de boucler dans rien
						
						//s'avancer vers s'arrête quand la distance augmente: arrete si c'est unmur, si c'est un palais si je suis décalé si précédente valeur était à 32 c'est palais mais si c'atit 50 c'ets que décalé
						// tant que méthode teste les 3 conditions. percevoir environnement toute les 50milliseconde, sinon bouvle trop vite. tant que vrai, si return
				}
			 //je suis sois à 32cm du palais si type=p, need tourner si décalé et si mur tourner aussi
		}	
		
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
		System.exit(0);
		}
		return type; 
}
	//tourner pour detect palais
	

	
	public void RotationRecherche() {
		try {
			double tab[]=new double[1000];
			//instruction de touner
			if ((detectInfinity()==-1)) {
				
			}
			else {
				int compteur=0; //donnera le nombre de valeur effectiveou un array avec des add comme ca direct
				roue.tournerD(360);
				while(roue.getEnMouvement()) {
					//je met dans mon tableau les différence de distance
					tab[compteur]=getDistance();
					//mettre un temps de 5millisec pour pas avoir trop de données rajout d'un compteur
					compteur++;
					Delay.msDelay(50);
				}
				//mtn je trie mon tableau en dégageant les valeurs <32,5 car c'est pas 
				//des palets et je vais vers la plus petite après
				int indice=-1;
				double min=1000;
				for(int j=0; j<tab.length;j++) {
					if(tab[j]<min && tab[j]>32.5) {
						min=tab[j];
						indice=j;
					}
				}
				//donc indice de tableau par rapport au minimum, le convertir en angle
				roue.tournerD(180/compteur*indice);
				//180 car que des demi tour pour détecter palais, 
				//on lance tourner moteur de l'angle et avancer!
			}
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
			System.exit(0);
		}
	}

	public TypeLastDistance CorrectionTrajectoire(float lastDistance) {
		TypeLastDistance type = new TypeLastDistance();
		float newDistance;
		roue.tournerD(5);
		newDistance=getDistance();
		if(newDistance<=lastDistance+2&&newDistance>=lastDistance-2) type.set('a', newDistance);
		else {
			roue.tournerG(10);
			newDistance=getDistance();
			if(newDistance<=lastDistance+2&&newDistance>=lastDistance-2) type.set('a', newDistance);
		}
		return type;
	}
	
	
		
		public static void main(String args[]) {
		
		CapteurUltrason a= new CapteurUltrason("S3");
		System.out.print(a.AvancerVers());
		/*float distance;
			distance=a.getDistance();
			System.out.println(distance);
			Delay.msDelay(3000);*/
		//a.detectPalais();
		//System.out.print(a.detectInfinity());
		//Delay.msDelay(3000);
		
		
		//System.out.print(a.getDistance());
		//Delay.msDelay(3000);
		//System.out.print(a.avancerPalaisMur());
		/*System.out.println();
		Delay.msDelay(3000);*/
		
	}
}

