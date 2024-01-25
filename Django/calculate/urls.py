from django.urls import path

from calculate import views

urlpatterns = [
    path("getStress1", views.getStress1),
    path("getStress2", views.getStress2),
]