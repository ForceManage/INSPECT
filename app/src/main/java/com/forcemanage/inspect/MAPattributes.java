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
        private String _image1;
        private String _note;

        public MAPattributes(int _projectId, int _catId, int _level, int _parent, String _label, int _child, int _aId, String _image1, String _note) {

            this._projectId = _projectId;
            this._catID = _catId;
            this._level = _level;
            this._parent = _parent;
            this._label = _label;
            this._child = _child;
            this._aId = _aId;
            this._image1 = _image1;
            this._note = _note;


        }



        public int get_projectId() {
            return _projectId;
        }

        public int get_catId() {
            return _catID;
        }
        public int get_level() {
        return _level;
    }

        public int get_parent() {
            return _parent;
        }

        public String get_label() {return _label; }

        public int get_child() {return _child; }

        public int get_aId() {return _aId; }

        public String get__image1() {return _image1; }

        public String get_note() {return _note; }


        public void set_projectId(int _projectId) {
            this._projectId = _projectId;
        }

        public void set_catId(int _catId) {
            this._catID = _catID;
        }

        public void set_level(int _catId) {
        this._level = _level;
    }
        public void set_parent(int _parent) {
            this._parent = _parent;
        }

        public void set_label(String _label) {
        this._label = _label;
    }

        public void set_child(int _child) {
        this._child = _child;
    }

        public void set_aId(int _aId) {
        this._aId = _aId;
    }

        public void set_image1(String _image1) {
        this._image1 = _image1;
    }

        public void set_note(String _note) {
        this._note = _note;
    }

}
