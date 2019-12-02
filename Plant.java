public abstract class Plant
{
    boolean isLiving;
    boolean isMature;
    boolean didReproduce;
    double seedlingChance;
    int timeAlive;
    int matureAge;
    int lifeSpan;
    int youngDiameter;
    int matureDiameter;
    int seedDispersal;
    
    /**
     * once the timeAlive variable becomes the same as the lifeSpan, the plant will die
     * 
     * At every progression of time, the timeAlive variable will increase, approaching
     *        lifeSpan
     */
    
    public void nextPlantYear()
    {
        if(isLiving)
        {
            timeAlive++;
            if(timeAlive >= lifeSpan)
                isLiving = false;
            else if(timeAlive >= matureAge)
                isMature = true;
        }
    }
    
    public boolean isLiving()  {
        return isLiving;}
    public boolean isMature()  {
        return isMature;}
    public boolean didReproduce()  {
        return didReproduce;}
    public double getSeedlingChance()  {
        return seedlingChance;}
    public int getTimeAlive()  {
        return timeAlive;}
    public int getMatureAge()  {
        return matureAge;}
    public int getLifeSpan()  {
        return lifeSpan;}
    public int getYoungDiameter()  {
        return youngDiameter;}
    public int getMatureDiameter()  {
        return matureDiameter;}
    public int getSeedDispersal()  {
        return seedDispersal;}
    
    public void setIsLiving(boolean living)  {isLiving = living;}
    public void setDidReproduce(boolean reproduce)  {didReproduce = reproduce;}
    
    public void randomize()
    {
        timeAlive = (int)(Math.random() * lifeSpan);
        checkMaturity();
    }
    
    public boolean checkMaturity()
    {
        if(timeAlive >= matureAge)
        {
            isMature = true;
            return true;
        }
        else
        {
            isMature = false;
            return false;
        }
    }
    
    public abstract Plant getBasePlant();
    public abstract Plant copyPlant();
    public abstract boolean isFiller();
}
