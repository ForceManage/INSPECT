package com.forcemanage.inspect.attributes;

public class A_Attributes {
    private int _num;
    private String _subCat;
    private int _type;
    private String _note;

    public A_Attributes(int _num, String _subCat, int _type, String _note) {
        this._num = _num;
        this._subCat = _subCat;
        this._type = _type;
        this._note = _note;
     }

    public int get_num() {
        return _num;
    }

    public String get_subCat() {return _subCat; }

    public int get_type() {
        return _type;
    }

    public String get_note() {
        return _note;
    }

    public void set_subCat(String _subCat) {
        this._subCat = _subCat;
    }

    public void set_type(int _type) {
        this._type = _type;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

 }
