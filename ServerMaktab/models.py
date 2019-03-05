# coding: utf-8
from sqlalchemy import Boolean, Column, DateTime, Float, ForeignKey, Integer, Table, Text
from sqlalchemy.orm import relationship
from sqlalchemy.sql.sqltypes import NullType
from flask_sqlalchemy import SQLAlchemy


db = SQLAlchemy()


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

class Sqliteseq(db.Model):
    __tablename__ = 'sqlite_sequence'

    name = db.Column(db.Text)
    seq = db.Column(db.Text)

