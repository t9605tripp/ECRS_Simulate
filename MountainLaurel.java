public class MountainLaurel extends Plant
{
   public MountainLaurel(int age)
   {
      isLiving = true;
      didReproduce = false;
      seedlingChance = 30;//Set to whatever?
      timeAlive = age;
      matureAge = 2;
      lifeSpan = 10;//Changed for demo purposes 75 is real value
      //Feet
      youngDiameter = 2;
      //Feet
      matureDiameter = 4;
      //Feet
      seedDispersal = 50;
      this.checkMaturity();
   }

   public Plant getBasePlant()
   {
      return new MountainLaurel(0);
   }
   
   public Plant copyPlant()
   {
      Plant flower = new MountainLaurel(timeAlive);
      if(!isLiving)
         flower.setIsLiving(false);
      flower.checkMaturity();
      return flower;
   }
  
   public boolean isFiller()
   {
      return false;
   }
}
