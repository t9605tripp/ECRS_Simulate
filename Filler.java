public class Filler extends Plant
{
    private Plant parent;

   public Filler(Plant attach)
   {
      this.setDummyVariables();
      parent = attach;
      /**
       * here, we could add an arraylist field to the Plant abstract class - 
       * then we could directly place the filler's coordinates in that
      **/
   }
   
   public void setDummyVariables()
   {
      isLiving = false;
      isMature = false;
      didReproduce = false;
      seedlingChance = 0;
      timeAlive = 0;
      matureAge = 0;
      lifeSpan = 0;
      youngDiameter = 0;
      matureDiameter = 0;
      seedDispersal = 0;
   }
   
   public Plant getParent()
   {
      return parent;}

   public Plant getBasePlant()
   {
      return new Filler(parent);}
   
   public Plant copyPlant()
   {
      return new Filler(parent);}
   
   public boolean isFiller()
   {
      return true;}
}