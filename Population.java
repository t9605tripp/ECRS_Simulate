import java.util.ArrayList;
import java.util.Random;

//This class is kind of an improved ArrayList
public class Population
{
   //INIT_POP can be whatever number you want! It's magic.
   private final int INITIAL_POPULATION = 100;
   //Random used in inserts.
   private Random r = new Random();
   //Size of the grid to be created (Should also be window size)
   int X_SIZE = 1920;
   int Y_SIZE = 1080;
   //Contains a list of 2D arrays so you can see past populations as well/
   private ArrayList<Plant[][]> pastGrids;
   //This is the 2D array which plants are stored on.
   private Plant[][] grid = new Plant[Y_SIZE][X_SIZE];
   //We take one interval to be a year.
   private ArrayList<Point> maturePlantPoints = new ArrayList<Point>();
   private ArrayList<Point> livingPlantPoints = new ArrayList<Point>();
   private ArrayList<Point> deadPlantPoints = new ArrayList<Point>();
   private ArrayList<Point> immaturePlantPoints = new ArrayList<Point>();

   private int year;
   private int numPlants;
   
   /**
	* Making the population an arraylist lets us grow the population a bit more easily
	*
	* If the Initial Population variable is 100, then the constructor creates
	*      an arraylist of 100 individual plants
	*
	* **/
   public Population(Plant[] types)
   {
      numPlants = 0;
      year = 0;
     //Don't think this line is needed, but need to test before removal. Lines 35 and 41 should work for this.
     //grid = new Plant[Y_SIZE][X_SIZE];
      pastGrids = new ArrayList<Plant[][]>();
   
      pastGrids.add(0, new Plant[Y_SIZE][X_SIZE]);
   
      for (int i = 0; i < types.length; i++) {
         insertInitial(INITIAL_POPULATION, types[i]);
      }
   
      grid = pastGrids.get(0);
   }

   public ArrayList<Point> getLivingPlantPoints(){
      return livingPlantPoints;
   }
   
   public ArrayList<Point> getMaturePlantPoints(){
      return maturePlantPoints;
   }

   public ArrayList<Point> getDeadPlantPoints(){
      return deadPlantPoints;
   }

   public Plant[][] get(int place)
   {
      return pastGrids.get(place);
   }

   public void nextYear()
   {
      updatePlantPoints();
     //System.out.print("Next Year: ");
      year++;
   
      if (pastGrids.size() == year) {
         Plant[][] copy = new Plant[Y_SIZE][X_SIZE];
         for (int i = 0; i < livingPlantPoints.size(); i++) {
            Point currentPoint = livingPlantPoints.get(i);
            Plant currentPlant = grid[currentPoint.getY()][currentPoint.getX()];
            //System.out.println(currentPoint.getX());
            //currentPlant.nextPlantYear();
            copy[currentPoint.getY()][currentPoint.getX()] = currentPlant.copyPlant();
            Plant copyPlant = copy[currentPoint.getY()][currentPoint.getX()];
            copyPlant.nextPlantYear();
         }
         for (int j = 0; j < deadPlantPoints.size(); j++) {
            Point curPoint = deadPlantPoints.get(j);
            Plant curPlant = grid[curPoint.getY()][curPoint.getX()];
            curPlant.nextPlantYear();
            copy[curPoint.getY()][curPoint.getX()] = curPlant.copyPlant();
         }
         pastGrids.add(year, copy);
         grid = pastGrids.get(year);
         this.reproduce();
      }
      else if(pastGrids.size() > year)
      {
       //System.out.println("Old");
         grid = pastGrids.get(year);
       // plants.clear();
       // for(int i = 0; i < 100/**pastPlants.get(year).size()*/; i++)
       // {
      	 // plants.add(pastPlants.get(year).get(i).copyPlant());
       // }
      }
      else
      {
         System.out.println("Error here");
      }
   }

   public void backYear()
   {
     //System.out.println("Back Year");
      year--;
      grid = pastGrids.get(year);
     // plants.clear();
     // for(int i = 0; i < pastPlants.get(year).size(); i++)
     // {
   	  // plants.add(pastPlants.get(year).get(i).copyPlant());
     // }
   }
   
   public void incrementYear(int time)
   {
      if(time==0)
         return;
      else if(time < 0)
         for(int i = 0; i < 0-time; i++)
         {
            this.backYear();
         }
      else if(time > 0)
         for(int i = 0; i < time; i++)
         {
            this.nextYear();
         }
   }
   
   public void setYear(int date)
   {
      if(date < 0 || date == year)
         return;
      else
      {
         this.incrementYear(date - year);
      }
   }
   
   public int getYear()
   {
      return year;
   }
   
   public int getNumPlants()
   {
      return numPlants;
   }

   /*
   public int getLivingNumber()
   {
	   int count = 0;
	   for(int i = 0; i < plants.size(); i++)
	   {
		   if(plants.get(i).isLiving())
		   {
			   count++;
		   }
	   }
	   return count;
   }
   */
   /* May be useful in the future.
   public void printOverview()
   {
	   System.out.println("Year: " + year);
	   System.out.println("Live: " + this.getLivingNumber());
	   System.out.println("Dead: " + this.getDeadNumber());
   }

   public int getDeadNumber()
   {
	   return plants.size() - this.getLivingNumber();
   }

   public int getMatureNumber()
   {
	   int count = 0;
	   for(int i = 0; i < plants.size(); i++)
	   {
		   if(plants.get(i).isMature())
		   {
			   count++;
		   }
	   }
	   return count;
   }
   */

   private void reproduce()
   {
     //insert new plant to grid if true.
      double chance;
     //Scan through the entire grid to see what plants reproduce.
      for (int i = 0; i < maturePlantPoints.size(); i++) {
         chance = Math.random() * 100;
      
       //insert a new plant if it makes the chance to repdouce.
         if (chance < grid[maturePlantPoints.get(i).getY()][maturePlantPoints.get(i).getX()].getSeedlingChance()){
            insert(grid[maturePlantPoints.get(i).getY()][maturePlantPoints.get(i).getX()].getBasePlant(),
               maturePlantPoints.get(i).getX(), maturePlantPoints.get(i).getY());
         }
      }
   
     //Destined for removal;
     /*for(int i = 0; i < X_SIZE; i++)
     {
   	  for (int j = 0; j < Y_SIZE; j++) {
   		  //Only reproduce if there is a living and mature plant at [row][column]
   		  if (grid[j][i] != null && grid[j][i].isLiving() && grid[j][i].isMature()) {
   				  chance = Math.random() * 100;
   				  if (chance < grid[j][i].getSeedlingChance()) {
   					  insert(grid[j][i].getBasePlant(), i, j);
   				  }
   		  }
   	  }
     }*/
   }

   /*
   insertInitial puts 100 plants on the grid. Must be an empty location.
	*/
   private void insertInitial(int initPop, Plant newPlant)
   {
   
      for (int i = 0; i < initPop; i++)
      {
         int xCoord = r.nextInt(X_SIZE);
         int yCoord = r.nextInt(Y_SIZE);
         while (pastGrids.get(year)[yCoord][xCoord] != null)
         {
            xCoord = r.nextInt(X_SIZE);
            yCoord = r.nextInt(Y_SIZE);
         }
         livingPlantPoints.add(new Point(xCoord, yCoord));
         
         pastGrids.get(year)[yCoord][xCoord] = newPlant.getBasePlant();
         pastGrids.get(year)[yCoord][xCoord].randomize();
         //System.out.println(pastGrids.get(year)[yCoord][xCoord].getTimeAlive());
         numPlants++;
      }
   }

   //After nextYear is called, more plants get put on the grid.
   private void insert(Plant newPlant, int parentX, int parentY) {
      int dispersal = newPlant.getSeedDispersal();
      int xMax;
      int xMin;
      int yMax;
      int yMin;
     //Don't let this go out of bounds!
      
      if (parentX - dispersal < 0) {
         return;
         /*xMin = 0;
         xMax = dispersal;*/
      }
      else if (parentX + dispersal > X_SIZE) {
         return;
         /*xMin = parentX - dispersal;
         xMax = X_SIZE;*/
      }
      else {xMin = parentX - dispersal; xMax = parentX + dispersal;}
     //Same thing for YCoord bounds
      if (parentY - dispersal < 0) {
         return;
         /*yMin = 0;
         yMax = dispersal;*/
      }
      else if (parentY + dispersal > Y_SIZE) {
         return;
         //yMin = parentY - dispersal;
         //yMax = Y_SIZE;
      }
      else {
         yMin = parentY - dispersal; yMax = parentY + dispersal;
      }
   
      int xCoord = r.nextInt(xMax - xMin) + xMin;
      int yCoord = r.nextInt(yMax - yMin) + yMin;
   
      while (grid[yCoord][xCoord] != null) {
         xCoord = r.nextInt(xMax - xMin) + xMin;
         yCoord = r.nextInt(yMax - yMin) + yMin;
      }
   
      livingPlantPoints.add(new Point(xCoord, yCoord));
      grid[yCoord][xCoord] = newPlant.getBasePlant();
   
      numPlants++;
   }

   /*
   This method updates all of the PlantPoints arraylists.
	*/
   private void updatePlantPoints() {
      immaturePlantPoints.clear();
      maturePlantPoints.clear();
      deadPlantPoints.clear();
   
      for (int i = livingPlantPoints.size()-1; i >= 0; --i)
      {
         //System.out.println(livingPlantPoints.get(i).getY());
         Plant checking = grid[livingPlantPoints.get(i).getY()][livingPlantPoints.get(i).getX()];
         //Update the mature plantList
         //Check for maturity first!
         //System.out.println(checking.getTimeAlive());
         if(checking.getTimeAlive() + 1 >= checking.getMatureAge()) {
            maturePlantPoints.add(new Point(livingPlantPoints.get(i).getX(), livingPlantPoints.get(i).getY()));
         }
         if(checking.getTimeAlive() + 1 >= checking.getLifeSpan()) {
            deadPlantPoints.add(new Point(livingPlantPoints.get(i).getX(), livingPlantPoints.get(i).getY()));
            livingPlantPoints.remove(i);
         }
         else {
            immaturePlantPoints.add(new Point(livingPlantPoints.get(i).getX(), livingPlantPoints.get(i).getY()));
         }
      }
   }

   public static void main(String[] args)
   {
      Plant[] insertPlant = new Plant[1];
      insertPlant[0] = new MountainLaurel(0);
      Population testPop = new Population(insertPlant);
      //testPop.insertInitial();
   }
}
