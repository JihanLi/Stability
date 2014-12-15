
br = [0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1];
sz = [1,5,10,20,30];
en = [0,0.05,0.1,0.15,0.2];
result = zeros(size(br,2),size(sz,2));

for i = 1:size(br,2)
    for j = 1:size(sz,2)
        filename = ['.\result\diff_BR' num2str(br(i)) '_SZ' num2str(sz(j)) '_EN0.02.txt'];
        diff=load(filename);
        result(i,j) = diff(end);
    end
end

mean_result = mean(result,2);

figure;
plot(br,mean_result);
xlabel('Balance Ratio');
ylabel('Difference of Convergence');

print(gcf,'-dpng','.\figures\balanceRatio_susceptibility1.png');


figure;
hold on;
for i = 1:size(result,1)
    r = rand();
    b = rand();
    g = rand();
    plot(sz,result(i,:),'Color',[r b g]);
end
xlabel('Clique Size');
ylabel('Difference of Convergence');
hold off;

print(gcf,'-dpng','.\figures\cliqueSize_susceptibility.png');



result = zeros(size(br,2),size(en,2));

for i = 1:size(br,2)
    for j = 1:size(en,2)
        filename = ['.\result\diff_BR' num2str(br(i)) '_EN' num2str(en(j)) '_SZ2.txt'];
        diff=load(filename);
        result(i,j) = diff(end);
    end
end

mean_result = mean(result,2);

figure;
plot(br,mean_result);
xlabel('Balance Ratio');
ylabel('Difference of Convergence');

print(gcf,'-dpng','.\figures\balanceRatio_susceptibility2.png');

figure;
hold on;
for i = 1:size(result,1)
    r = rand();
    b = rand();
    g = rand();
    plot(en,result(i,:),'Color',[r b g]);
end
xlabel('New Edge Number');
ylabel('Difference of Convergence');
hold off;

print(gcf,'-dpng','.\figures\newEdgeNumber_susceptibility.png');
