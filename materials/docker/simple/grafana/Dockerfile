FROM grafana/grafana
EXPOSE 3000

COPY datasource-prometheus.yaml /etc/grafana/provisioning/datasources/
COPY dashboard-provider.yaml /etc/grafana/provisioning/dashboards/
COPY ps-monitoring-dashboard.json /var/lib/grafana/dashboards/

COPY init.sh .
RUN ./init.sh