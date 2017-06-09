# coding: utf-8
from sqlalchemy import Column, Float, ForeignKey, Integer, String, Text
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
metadata = Base.metadata


class Follower(Base):
    __tablename__ = 'Follower'

    FollowerID = Column(String(255), nullable=False, index=True)
    FollowingID = Column(String(255), nullable=False)
    ID = Column(Integer, primary_key=True)


class InfoContent(Base):
    __tablename__ = 'InfoContent'

    ContentID = Column(Integer, primary_key=True)
    ProfileID = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    ContentInfo = Column(Text, nullable=False)

    Profile = relationship(u'Profile')


class Picture(Base):
    __tablename__ = 'Pictures'

    PictureID = Column(Integer, primary_key=True)
    ProfileID = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    Link = Column(Text, nullable=False)

    Profile = relationship(u'Profile')


class Profile(Base):
    __tablename__ = 'Profile'

    ProfileID = Column(String(255), primary_key=True)
    Name = Column(String(255), nullable=False)
    BirthDate = Column(String(255))
    Gender = Column(String(6))
    Father = Column(String(255))
    Mother = Column(String(255))
    Children = Column(Integer)
    DeathDate = Column(String(255))
    NickName = Column(String(255))


class ProfileOccupation(Base):
    __tablename__ = 'ProfileOccupation'

    Occupation = Column(String(255), nullable=False, index=True)
    ProfileID = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    Id = Column(Integer, primary_key=True)

    Profile = relationship(u'Profile')


class Reference(Base):
    __tablename__ = 'References'

    Id = Column(Integer, primary_key=True)
    ProfileId = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    Url = Column(String(255), nullable=False)

    Profile = relationship(u'Profile')


class ProfileImage(Base):
    __tablename__ = 'ProfileImage'

    ID = Column(Integer, primary_key=True)
    ProfileID = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    Image = Column(String(255), nullable=False)
    Profile = relationship(u'Profile')


class SocialNetworkStatus(Base):
    __tablename__ = 'SocialNetworkStatus'

    StatusID = Column(Integer, primary_key=True)
    ProfileID = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    PageRank = Column(Float, nullable=False)

    Profile = relationship(u'Profile')


class Spouce(Base):
    __tablename__ = 'Spouce'

    SpouceId = Column(Integer, primary_key=True)
    ProfileId = Column(ForeignKey(u'Profile.ProfileID', ondelete=u'CASCADE', onupdate=u'CASCADE'), nullable=False,
                       index=True)
    Name = Column(String(255), nullable=False)

    Profile = relationship(u'Profile')


class Tweet(Base):
    __tablename__ = 'Tweets'

    TweetID = Column(String(255), primary_key=True)
    TweetContent = Column(Text, nullable=False)
    ProfileID = Column(String(255), nullable=False)
    PostDate = Column(String(255), nullable=False)
