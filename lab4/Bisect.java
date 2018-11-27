public class Bisect {

   private double tolerance;
   private int maxIterations;
   private polynomial func;

   public Bisect(polynomial f) {
	  func = f;
      tolerance = 0.000001;
      maxIterations = 50;
   }

   public Bisect(double tol, polynomial f) {
	  func = f;
      tolerance = tol;
      maxIterations = 50;
   }

   public Bisect(int maxIter, polynomial f) {
	  func = f;
      tolerance = 0.000001;
      maxIterations = maxIter;
   }

   public Bisect(double tol, int maxIter, polynomial f) {
	  func = f;
      tolerance = tol;
      maxIterations = maxIter;
   }

   public double getTolerance() {
      return tolerance;
   }

   public void setTolerance(double tol) {
      if (tol > 0)
         tolerance = tol;
   }

   public double getMaxIterations() {
      return maxIterations;
   }

   public void setMaxIterations(int maxIter) {
      if (maxIter > 0)
         maxIterations = maxIter;
   }


   public double run(double x1, double x2) throws RootNotFound {
	   
      int iterNum = 1;
      double f1, f2, fmid;
      double mid = 0;

      do {
         f1 = func.eval(x1);
         f2 = func.eval(x2);
         
         if (f1 * f2 > 0) {
            throw new RootNotFound();
         }
         
         mid = (x1 + x2) / 2; 
         fmid = func.eval(mid);
         if (fmid * f1 < 0) {
            x2 = mid;
         } else {
            x1 = mid;
         }
         iterNum++;
      } while (conditions(x1, x2, fmid, iterNum));
      
      if (iterNum >= maxIterations) {
          throw new RootNotFound();
      }
      
      return mid;
   }

   public boolean conditions(double x1, double x2, double fmid, int iterNum) {
      boolean condition1 = false;
      boolean condition2 = false;
      boolean condition3 = false;

      if (Math.abs(x1 - x2) / 2 >= tolerance) {
         condition1 = true;
      }

      if (Math.abs(fmid) > tolerance) {
         condition2 = true;
      }

      if (iterNum <= maxIterations) {
         condition3 = true;
      }

      return condition1 && condition2 && condition3;
   }
   
   public interface polynomial {
	   public double eval(double value);
   }
   
   public class RootNotFound extends Exception {
	   
   }
}