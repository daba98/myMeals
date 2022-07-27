package de.die_bartmanns.daniel.mymeal.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UnitCounts")
public class UnitCount {

    @NonNull
    @PrimaryKey
    private String ingredient;
    private int soupSpoonCount;
    private int teaSpoonCount;
    private int grammCount;
    private int kiloCount;
    private int milliliterCount;
    private int literCount;
    private int pinchCount;
    private int pieceCount;
    private int packageCount;
    @ColumnInfo(defaultValue = "0")
    private int sliceCount;
    @ColumnInfo(defaultValue = "0")
    private int bunchCount;
    @ColumnInfo(defaultValue = "0")
    private int cupCount;
    @ColumnInfo(defaultValue = "0")
    private int canCount;

    public UnitCount(String ingredient){
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getSoupSpoonCount() {
        return soupSpoonCount;
    }

    public void setSoupSpoonCount(int soupSpoonCount) {
        this.soupSpoonCount = soupSpoonCount;
    }

    public int getTeaSpoonCount() {
        return teaSpoonCount;
    }

    public void setTeaSpoonCount(int teaSpoonCount) {
        this.teaSpoonCount = teaSpoonCount;
    }

    public int getGrammCount() {
        return grammCount;
    }

    public void setGrammCount(int grammCount) {
        this.grammCount = grammCount;
    }

    public int getKiloCount() {
        return kiloCount;
    }

    public void setKiloCount(int kiloCount) {
        this.kiloCount = kiloCount;
    }

    public int getMilliliterCount() {
        return milliliterCount;
    }

    public void setMilliliterCount(int milliliterCount) {
        this.milliliterCount = milliliterCount;
    }

    public int getLiterCount() {
        return literCount;
    }

    public void setLiterCount(int literCount) {
        this.literCount = literCount;
    }

    public int getPinchCount() {
        return pinchCount;
    }

    public void setPinchCount(int pinchCount) {
        this.pinchCount = pinchCount;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    public int getSliceCount() {
        return sliceCount;
    }

    public void setSliceCount(int sliceCount) {
        this.sliceCount = sliceCount;
    }

    public int getBunchCount() {
        return bunchCount;
    }

    public void setBunchCount(int bunchCount) {
        this.bunchCount = bunchCount;
    }

    public int getCupCount() {
        return cupCount;
    }

    public void setCupCount(int cupCount) {
        this.cupCount = cupCount;
    }

    public int getCanCount() {
        return canCount;
    }

    public void setCanCount(int canCount) {
        this.canCount = canCount;
    }

    public void increaseSoupSpoonCount(){
        soupSpoonCount++;
    }

    public void increaseTeaSpoonCount(){
        teaSpoonCount++;
    }

    public void increaseGrammCount(){
        grammCount++;
    }

    public void increaseKiloCount(){
        kiloCount++;
    }

    public void increaseMilliliterCount(){
        milliliterCount++;
    }

    public void increaseLiterCount(){
        literCount++;
    }

    public void increasePinchCount(){
        pinchCount++;
    }

    public void increasePieceCount(){
        pieceCount++;
    }

    public void increasePackageCount(){
        packageCount++;
    }

    public void increaseSliceCount(){
        sliceCount++;
    }

    public void increaseBunchCount(){
        bunchCount++;
    }

    public void increaseCupCount(){
        cupCount++;
    }

    public void increaseCanCount(){
        canCount++;
    }

    public int getMaxCount(){
        int max = 0;
        int[] counts = new int[]{soupSpoonCount, teaSpoonCount, grammCount, kiloCount, milliliterCount, literCount, pinchCount, pieceCount, packageCount,
        sliceCount, bunchCount, cupCount, canCount};
        for (int count : counts) {
            if (count > max)
                max = count;
        }
        return max;
    }
}
