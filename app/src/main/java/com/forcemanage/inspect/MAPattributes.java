package com.forcemanage.inspect;

public class MAPattributes {
    /**
     * Created by DAP on 10/03/2020.
     */


        private int _projectId;
        private int _catID;
        private int _level;
        private int _parent;
        private String _label;
        private int _child;
        private int _aId;
        private int _iId;
        private String _image1;
        private String _note;

        public MAPattributes(int ProjectID, int CatID, int Level, int Parent, String Label, int Child, int aID, int iID, String image1, String Notes) {

            this._projectId = ProjectID;
            this._catID = CatID;
            this._level = Level;
            this._parent = Parent;
            this._label = Label;
            this._child = Child;
            this._aId = aID;
            this._iId = iID;
            this._image1= image1;
            this._note = Notes;


        }



        public int getprojectId() {
            return _projectId;
        }

        public int getcatId() {
            return _catID;
        }
        public int getlevel() {
        return _level;
    }

        public int getparent() {
            return _parent;
        }

        public String getlabel() {return _label; }

        public int getchild() {return _child; }

        public int getaId() {return _aId; }

        public int getiId() {return _iId; }

        public String getimage1() {return _image1; }

        public String getnote() {return _note; }


        public void setprojectId(int _projectId) {this._projectId = _projectId;  }

        public void setcatId(int _catId) {
            this._catID = _catID;
        }

        public void setlevel(int _catId) {
        this._level = _level;
    }
        public void setparent(int _parent) {
            this._parent = _parent;
        }

        public void setlabel(String _label) {
        this._label = _label;
    }

        public void setchild(int _child) {
        this._child = _child;
    }

        public void setaId(int _aId) {
        this._aId = _aId;
    }

        public void setiId(int _iId) {
        this._iId = _iId;
    }

        public void set_image1(String _image1) {
        this._image1 = _image1;
    }

        public void set_note(String _note) {
        this._note = _note;
    }

}
