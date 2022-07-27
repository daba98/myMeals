package de.die_bartmanns.daniel.mymeal.data.database;

import android.content.Context;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.OwnCategory;
import de.die_bartmanns.daniel.mymeal.data.Unit;
import de.die_bartmanns.daniel.mymeal.data.UnitCount;

public class DatabaseServiceImpl implements DatabaseService{

    private final MealDao mealDao;
    private final UnitCountDao unitCountDao;
    private final OwnCategoryDao ownCategoryDao;


    public DatabaseServiceImpl(Context context){
        MealDatabase database = MealDatabase.getDatabase(context);
        mealDao = database.mealDao();
        unitCountDao = database.unitCountDao();
        ownCategoryDao = database.ownCategoryDao();
    }

    @Override
    public void insertMeal(Meal meal) {
        mealDao.insertMeal(meal);
    }

    @Override
    public void insertMeals(List<Meal> meals) {
        mealDao.insertMeals(meals);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealDao.updateMeal(meal);
    }

    @Override
    public void updateMeals(List<Meal> meals) {
        for(Meal meal : meals)
            updateMeal(meal);
    }

    @Override
    public Meal getMeal(long mealId) {
        return mealDao.getMeal(mealId);
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealDao.getAllMeals();
    }

    @Override
    public List<Long> getAllMealIds() {
        return mealDao.getAllMealIds();
    }

    @Override
    public List<Long> getAllMealIdsWithType(MealType type) {
        return mealDao.getAllMealIds(type);
    }

    @Override
    public List<Long> getAllMealIdsWithCategory(String category) {
        return mealDao.getAllMealIdsWithCategory(category);
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealDao.deleteMeal(meal);
    }

    @Override
    public void deleteMeal(long id) {
        deleteMeal(getMeal(id));
    }


    @Override
    public void addCount(String ingredient, Unit unit) {
        UnitCount unitCount = unitCountDao.getUnitCount(ingredient.trim());
        if(unitCount == null) {
            unitCount = new UnitCount(ingredient.trim());
            unitCountDao.insertUnitCount(unitCount);
        }
        if(unit.equals(Unit.ESSLOEFFEL))
            unitCount.increaseSoupSpoonCount();
        else if(unit.equals(Unit.TEELOEFFEL))
            unitCount.increaseTeaSpoonCount();
        else if(unit.equals(Unit.GRAMM))
            unitCount.increaseGrammCount();
        else if(unit.equals(Unit.KILO))
            unitCount.increaseKiloCount();
        else if(unit.equals(Unit.MILLILITER))
            unitCount.increaseMilliliterCount();
        else if(unit.equals(Unit.LITER))
            unitCount.increaseLiterCount();
        else if(unit.equals(Unit.PRISE))
            unitCount.increasePinchCount();
        else if(unit.equals(Unit.STUECK))
            unitCount.increasePieceCount();
        else if(unit.equals(Unit.PACKUNG))
            unitCount.increasePackageCount();
        else if(unit.equals(Unit.SCHEIBE))
            unitCount.increaseSliceCount();
        else if(unit.equals(Unit.BUND))
            unitCount.increaseBunchCount();
        else if(unit.equals(Unit.TASSE))
            unitCount.increaseCupCount();
        else if(unit.equals(Unit.DOSE))
            unitCount.increaseCanCount();

        unitCountDao.updateUnitCount(unitCount);
    }

    @Override
    public Unit getMostLikelyUnit(String ingredient) {
        UnitCount unitCount = unitCountDao.getUnitCount(ingredient);
        if(unitCount == null)
            return Unit.ESSLOEFFEL;
        int maxCount = unitCount.getMaxCount();
        if(maxCount == unitCount.getSoupSpoonCount())
            return Unit.ESSLOEFFEL;
        else if(maxCount == unitCount.getTeaSpoonCount())
            return Unit.TEELOEFFEL;
        else if(maxCount == unitCount.getGrammCount())
            return Unit.GRAMM;
        else if(maxCount == unitCount.getKiloCount())
            return Unit.KILO;
        else if(maxCount == unitCount.getMilliliterCount())
            return Unit.MILLILITER;
        else if(maxCount == unitCount.getLiterCount())
            return Unit.LITER;
        else if(maxCount == unitCount.getPinchCount())
            return Unit.PRISE;
        else if(maxCount == unitCount.getPieceCount())
            return Unit.STUECK;
        else if(maxCount == unitCount.getPackageCount())
            return Unit.PACKUNG;
        else if(maxCount == unitCount.getSliceCount())
            return Unit.SCHEIBE;
        else if(maxCount == unitCount.getBunchCount())
            return Unit.BUND;
        else if(maxCount == unitCount.getCupCount())
            return Unit.TASSE;
        else
            return Unit.DOSE;
    }

    @Override
    public void insertCategory(String name) {
        if(!containsCategory(name)) {
            OwnCategory category = new OwnCategory(name);
            ownCategoryDao.insertCategory(category);
        }
    }

    private boolean containsCategory(String name){
        return ownCategoryDao.getCategory(name) != null;
    }

    @Override
    public OwnCategory getCategory(String name) {
        return ownCategoryDao.getCategory(name);
    }

    @Override
    public List<String> getAllCategoryNames() {
        return ownCategoryDao.getAllCategoryNames();
    }

    @Override
    public void deleteCategory(OwnCategory category) {
        ownCategoryDao.deleteCategory(category);
    }
}
