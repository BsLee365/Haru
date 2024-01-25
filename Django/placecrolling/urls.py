from django.urls import path

from placecrolling import views

urlpatterns = [
    path('placeCrawl', views.placeCrawl),
]