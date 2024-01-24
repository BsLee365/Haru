from django.urls import path

from text import views

urlpatterns = [
    path('feedKeyword', views.feedKeyword),
]