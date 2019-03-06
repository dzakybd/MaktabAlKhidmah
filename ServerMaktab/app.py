
from flask import Flask, jsonify, request, make_response
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import json

app = Flask(__name__)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///database.db'
app.config['SECRET_KEY'] = "dzaky"
db = SQLAlchemy(app)
ma = Marshmallow(app)



class Akun(db.Model):
    __tablename__ = 'akun'

    akunid = db.Column(db.Integer, primary_key=True)
    nama = db.Column(db.Text)
    nohp = db.Column(db.Text)
    peran = db.Column(db.Integer)
    daerahpdkr = db.Column(db.ForeignKey(u'daerah.daerahid'))
    rombongankrjamaah = db.Column(db.ForeignKey(u'rombongan.rombonganid'))
    aktif = db.Column(db.Boolean)
    lat = db.Column(db.Float)
    long = db.Column(db.Float)
    keteranganpengurus = db.Column(db.Text)

    daerah = db.relationship(u'Daerah', primaryjoin='Akun.daerahpdkr == Daerah.daerahid', backref=u'akuns')
    rombongan = db.relationship(u'Rombongan', primaryjoin='Akun.rombongankrjamaah == Rombongan.rombonganid', backref=u'akuns')


class Daerah(db.Model):
    __tablename__ = 'daerah'

    daerahid = db.Column(db.Integer, primary_key=True)
    namadaerah = db.Column(db.Text)


class Jadwalmakan(db.Model):
    __tablename__ = 'jadwalmakan'

    jadwalmakanid = db.Column(db.Integer, primary_key=True)
    nama = db.Column(db.Text)
    waktu = db.Column(db.DateTime)


class Maktab(db.Model):
    __tablename__ = 'maktab'

    maktabid = db.Column(db.Integer, primary_key=True)
    namapemilik = db.Column(db.Text)
    nohppemilik = db.Column(db.Text)
    alamat = db.Column(db.Text)
    lat = db.Column(db.Float)
    long = db.Column(db.Float)
    kategori = db.Column(db.Integer)
    kapasitas = db.Column(db.Integer)
    aktif = db.Column(db.Boolean)
    riwayatdaerah = db.Column(db.ForeignKey(u'daerah.daerahid'))

    daerah = db.relationship(u'Daerah', primaryjoin='Maktab.riwayatdaerah == Daerah.daerahid', backref=u'maktabs')


class Parkir(db.Model):
    __tablename__ = 'parkir'

    parkirid = db.Column(db.Integer, primary_key=True)
    nama = db.Column(db.Text)
    alamat = db.Column(db.Text)
    lat = db.Column(db.Float)
    long = db.Column(db.Float)
    status = db.Column(db.Integer)
    kategori = db.Column(db.Integer)


class Pengambilan(db.Model):
    __tablename__ = 'pengambilan'

    pengambilanid = db.Column(db.Integer, primary_key=True)
    rombonganid = db.Column(db.ForeignKey(u'rombongan.rombonganid'))
    jadwalmakanid = db.Column(db.ForeignKey(u'jadwalmakan.jadwalmakanid'))
    waktu = db.Column(db.DateTime)
    status = db.Column(db.Integer)

    jadwalmakan = db.relationship(u'Jadwalmakan', primaryjoin='Pengambilan.jadwalmakanid == Jadwalmakan.jadwalmakanid', backref=u'pengambilans')
    rombongan = db.relationship(u'Rombongan', primaryjoin='Pengambilan.rombonganid == Rombongan.rombonganid', backref=u'pengambilans')


class Rombongan(db.Model):
    __tablename__ = 'rombongan'

    rombonganid = db.Column(db.Integer, primary_key=True)
    namarombongan = db.Column(db.Text)
    koordinator = db.Column(db.ForeignKey(u'akun.akunid'))
    namapo = db.Column(db.Text)
    jeniskendaraan = db.Column(db.Integer)
    nokendaraan = db.Column(db.Text)
    jumlahjamaah = db.Column(db.Integer)
    waktuberangkat = db.Column(db.DateTime)
    waktutiba = db.Column(db.DateTime)
    kategori = db.Column(db.Integer)
    alokasimaktab = db.Column(db.ForeignKey(u'maktab.maktabid'))
    alokasiparkir = db.Column(db.ForeignKey(u'parkir.parkirid'))

    maktab = db.relationship(u'Maktab', primaryjoin='Rombongan.alokasimaktab == Maktab.maktabid', backref=u'rombongans')
    parkir = db.relationship(u'Parkir', primaryjoin='Rombongan.alokasiparkir == Parkir.parkirid', backref=u'rombongans')
    akun = db.relationship(u'Akun', primaryjoin='Rombongan.koordinator == Akun.akunid', backref=u'rombongans')


param_akunid = "akunid"
param_nama = "nama"
param_nohp = "nohp"
param_peran = "peran"
param_daerahpdkr = "daerahpdkr"
param_rombongankrjamaah = "rombongankrjamaah"
param_aktif = "aktif"
param_lat = "lat"
param_lng = "lng"
param_keteranganpengurus = "keteranganpengurus"
param_daerahid = "daerahid"
param_namadaerah = "namadaerah"
param_judul = "judul"
param_nilai = "nilai"
param_waktu = "waktu"
param_jadwalmakanid = "jadwalmakanid"
param_maktabid = "maktabid"
param_namapemilik = "namapemilik"
param_nohppemilik = "nohppemilik"
param_alamat = "alamat"
param_kategori = "kategori"
param_kapasitas = "kapasitas"
param_riwayatdaerah = "riwayatdaerah"
param_parkirid = "parkirid"
param_status = "status"
param_rombonganid = "rombonganid"
param_namarombongan = "namarombongan"
param_koordinator = "koordinator"
param_namapo = "namapo"
param_jeniskendaraan = "jeniskendaraan"
param_jumlahjamaah = "jumlahjamaah"
param_waktuberangkat = "waktuberangkat"
param_waktutiba = "waktutiba"
param_alokasimaktab = "alokasimaktab"
param_alokasiparkir = "alokasiparkir"
    
class UserSchema(ma.Schema):
    class Meta:
        fields = ('akunid', 'nama', 'nohp')


@app.route('/')
def hello_world():

    users_schema = UserSchema(many=True)

    all_users = Akun.query.all()
    result = users_schema.dump(all_users)
    return jsonify(result.data)


@app.route('/login', methods=['POST'])
def login():
    return "result"


@app.route('/data/akun', methods=['POST'])
def data_akun():
    return "result"


@app.route('/data/jadwalmakan', methods=['POST'])
def data_jadwalmakan():
    return "result"


@app.route('/data/maktab', methods=['POST'])
def data_maktab():
    return "result"


@app.route('/data/parkir', methods=['POST'])
def data_parkir():
    return "result"


@app.route('/data/rombongan', methods=['POST'])
def data_rombongan():
    return "result"


@app.route('/data/daerah', methods=['POST'])
def data_daerah():
    return "result"


@app.route('/data/pengambilanmakanan', methods=['POST'])
def data_pengambilanmakanan():
    return "result"


@app.route('/list/pengambilanmakanan', methods=['POST'])
def list_pengambilanmakanan():
    return "result"


@app.route('/list/penghunimaktab', methods=['POST'])
def list_penghunimaktab():
    return "result"


@app.route('/list/penghuniparkir', methods=['POST'])
def list_penghuniparkir():
    return "result"


@app.route('/list/daerah', methods=['POST'])
def list_daerah():
    return "result"


@app.route('/menu/akun', methods=['POST'])
def menu_akun():
    return "result"


@app.route('/menu/dashboard', methods=['POST'])
def menu_dashboard():
    return "result"


@app.route('/menu/jadwalmakan', methods=['POST'])
def menu_jadwalmakan():
    return "result"


@app.route('/menu/maktab', methods=['POST'])
def menu_maktab():
    return "result"


@app.route('/menu/rombongan', methods=['POST'])
def menu_rombongan():
    return "result"


@app.route('/menu/parkir', methods=['POST'])
def menu_parkir():
    return "result"


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', threaded=True)
