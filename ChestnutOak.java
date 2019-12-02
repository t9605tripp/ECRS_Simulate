public class ChestnutOak extends Plant
{
    public ChestnutOak(int age)
    {
        isLiving = true;
        didReproduce = false;
        seedlingChance = 1.37;
        timeAlive = age;
        matureAge = 15;
        lifeSpan = 350;
        //Feet
        youngDiameter = 1;
        //Feet
        matureDiameter = 3;
        //Feet
        seedDispersal = 80;
        this.checkMaturity();
    }

   public Plant getBasePlant()
   {
      return new ChestnutOak(0);
   }

   public Plant copyPlant()
   {
      Plant tree = new MountainLaurel(timeAlive);
      if(!isLiving)
         tree.setIsLiving(false);
      tree.checkMaturity();
      return tree;
   }

   public boolean isFiller()
   {
      return false;
   }
}